package pl.khuzzuk.battles.stages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.functions.Actions;
import pl.khuzzuk.battles.model.DamageAction;
import pl.khuzzuk.battles.model.DamageOrder;
import pl.khuzzuk.battles.model.Side;
import pl.khuzzuk.functions.MultiGate;
import pl.khuzzuk.messaging.Bus;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DamageResolver {
    private Map<Side, BattleSetup> battleSetups;
    private SortedMap<DamageOrder, List<Card>> leftDamageStage;
    private SortedMap<DamageOrder, List<Card>> centerDamageStage;
    private SortedMap<DamageOrder, List<Card>> rightDamageStage;
    private DamageOrder currentOrder;

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
        MultiGate whenReady = MultiGate.of(2, this::restartDamageStage, Actions.EMPTY_ACTION);

        bus.<BattleSetup>setReaction(Stages.BATTLE_START_PLAYER.name(), setup -> {
            battleSetups.put(Side.PLAYER, setup);
            whenReady.on(0);
        });
        bus.<BattleSetup>setReaction(Stages.BATTLE_START_OPPONENT.name(), setup -> {
            battleSetups.put(Side.OPPONENT, setup);
            whenReady.on(1);
        });

        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_LEFT, Stages.SHOW_DAMAGE_STAGE_LEFT, () -> leftDamageStage);
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_CENTER, Stages.SHOW_DAMAGE_STAGE_CENTER, () -> centerDamageStage);
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_RIGHT, Stages.SHOW_DAMAGE_STAGE_RIGHT, () -> rightDamageStage);
    }

    private void setDamageStageResponse(Bus bus, Enum<?> requestType, Enum<?> responseType,
                                        Supplier<SortedMap<DamageOrder, List<Card>>> damageStage) {
        bus.setResponse(requestType.name(), () -> bus.send(responseType.name(), getDamageStageFilter(damageStage.get())));
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
        cards.forEach(card -> damageStage.computeIfAbsent(card.getDamageOrder(),
                any -> new ArrayList<>()).add(card));
    }

    private void resolveLeftWing(DamageAction damageAction) {
        resolveDamageAction(damageAction,
                battleSetups.get(damageAction.getDefendingSide()).getLeft());
    }

    private void resolveDamageAction(DamageAction damageAction, Deck defenders) {
        defenders.getCards().remove(damageAction.getDefender());
        Card afterDamage = damageAction.getDefender()
                .withMorale(calculateDamage(damageAction.getAttacker(), damageAction.getDefender()));
        defenders.getCards().add(afterDamage);
    }

    private int calculateDamage(Card attacker, Card defender) {
        return defender.getMorale() - (Math.max(0, attacker.getStrength() - defender.getDefence()));
    }
}
