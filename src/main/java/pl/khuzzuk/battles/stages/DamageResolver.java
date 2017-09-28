package pl.khuzzuk.battles.stages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.model.DamageAction;
import pl.khuzzuk.battles.model.Reach;
import pl.khuzzuk.battles.model.Side;
import pl.khuzzuk.messaging.Bus;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DamageResolver {
    private Bus bus;
    private Map<Side, BattleSetup> battleSetups;
    private int leftDamageStage;
    private int centerDamageStage;
    private int rightDamageStage;
    private List<Card> notUsed;

    public static DamageResolver get(Bus bus) {
        DamageResolver damageResolver = new DamageResolver();
        damageResolver.notUsed = new ArrayList<>();
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

        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_LEFT, Stages.SHOW_DAMAGE_STAGE_LEFT,
                () -> battleSetups.get(Side.PLAYER).getLeft(), () -> battleSetups.get(Side.OPPONENT).getLeft());
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_CENTER, Stages.SHOW_DAMAGE_STAGE_CENTER,
                () -> battleSetups.get(Side.PLAYER).getCenter(), () -> battleSetups.get(Side.OPPONENT).getCenter());
        setDamageStageResponse(bus, Stages.GET_DAMAGE_STAGE_RIGHT, Stages.SHOW_DAMAGE_STAGE_RIGHT,
                () -> battleSetups.get(Side.PLAYER).getRight(), () -> battleSetups.get(Side.OPPONENT).getRight());
    }

    private void setDamageStageResponse(Bus bus, Enum<?> requestType, Enum<?> responseType,
                                        Supplier<Deck> playerDeck, Supplier<Deck> opponentDeck) {
        bus.setReaction(requestType.name(), () -> bus.send(responseType.name(), getDamageStageFilter(
                playerDeck.get(), opponentDeck.get())));
    }

    private Predicate<Card> getDamageStageFilter(Deck playerDeck, Deck opponentDeck) {
        int actualState = Stream.concat(playerDeck.getCards().stream(), opponentDeck.getCards().stream())
                .filter(notUsed::contains)
                .map(Card::getReach)
                .mapToInt(Reach::getDistance)
                .max().orElse(0);
        return card -> notUsed.contains(card) && card.getReach().getDistance() >= actualState;
    }

    private void restartDamageStage() {
        notUsed.clear();
        battleSetups.values().forEach(battleSetup -> notUsed.addAll(battleSetup.getAllCardsInPlay()));
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
