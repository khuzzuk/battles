package pl.khuzzuk.battles.stages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.model.DamageAction;
import pl.khuzzuk.battles.model.DamageOrder;
import pl.khuzzuk.battles.model.Side;
import pl.khuzzuk.messaging.Bus;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DamageResolver {
    private Bus bus;
    private Map<Side, BattleSetup> battleSetups;
    private SortedMap<DamageOrder, List<Card>> leftDamageStage;
    private SortedMap<DamageOrder, List<Card>> centerDamageStage;
    private SortedMap<DamageOrder, List<Card>> rightDamageStage;
    private static Function<Card, Integer> distanceMapper = card -> card.getReach().getDistance();

    public static DamageResolver get(Bus bus) {
        DamageResolver damageResolver = new DamageResolver();
        damageResolver.leftDamageStage = new TreeMap<>();
        damageResolver.centerDamageStage = new TreeMap<>();
        damageResolver.rightDamageStage = new TreeMap<>();
        damageResolver.subscribeOn(bus);
        return damageResolver;
    }

    private void subscribeOn(Bus bus) {
        battleSetups = new EnumMap<>(Side.class);
        this.bus = bus;
        bus.<BattleSetup>setReaction(Stages.BATTLE_START_PLAYER.name(), setup -> {
            battleSetups.put(Side.PLAYER, setup);
            restartDamageStage();
        });
        bus.<BattleSetup>setReaction(Stages.BATTLE_START_OPPONENT.name(), setup -> {
            battleSetups.put(Side.OPPONENT, setup);
            restartDamageStage();
        });

        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_LEFT, Stages.SHOW_DAMAGE_STAGE_LEFT, () -> leftDamageStage);
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_CENTER, Stages.SHOW_DAMAGE_STAGE_CENTER, () -> centerDamageStage);
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_RIGHT, Stages.SHOW_DAMAGE_STAGE_RIGHT, () -> rightDamageStage);
    }

    private void setDamageStageResponse(Bus bus, Enum<?> requestType, Enum<?> responseType,
                                        Supplier<SortedMap<DamageOrder, List<Card>>> damageStage) {
        bus.setReaction(requestType.name(), () -> bus.send(responseType.name(), getDamageStageFilter(damageStage.get())));
    }

    private Predicate<Card> getDamageStageFilter(SortedMap<DamageOrder, List<Card>> damageStage) {
        return card -> damageStage.get(damageStage.lastKey()).contains(card);
    }

    private void restartDamageStage() {
        BattleSetup playerSetup = battleSetups.get(Side.PLAYER);
        BattleSetup opponentSetup = battleSetups.get(Side.OPPONENT);

        mapCards(playerSetup.getLeft().getCards(), leftDamageStage);
        mapCards(opponentSetup.getLeft().getCards(), leftDamageStage);
        mapCards(playerSetup.getCenter().getCards(), centerDamageStage);
        mapCards(opponentSetup.getCenter().getCards(), centerDamageStage);
        mapCards(playerSetup.getRight().getCards(), rightDamageStage);
        mapCards(opponentSetup.getRight().getCards(), rightDamageStage);
    }

    private void mapCards(List<Card> cards, Map<DamageOrder, List<Card>> damageStage) {
        cards.forEach(card -> damageStage.computeIfAbsent(
                new DamageOrder(card.getReach(), card.getSpeed()),
                damageOrder -> new ArrayList<>()).add(card));
    }

    private void resolveLeftWing(DamageAction damageAction) {
        resolveDamageAction(damageAction,
                battleSetups.get(damageAction.getDefendingSide()).getLeft());
    }

    private void resolveDamageAction(DamageAction damageAction, Deck defenders) {
        defenders.getCards().remove(damageAction.getDefender());
        defenders.getCards().add(damageAction.getDefender()
                .withMorale(calculateDamage(damageAction.getAttacker(), damageAction.getDefender())));
    }

    private int calculateDamage(Card attacker, Card defender) {
        return defender.getMorale() - (Math.max(0, attacker.getStrength() - defender.getDefence()));
    }
}
