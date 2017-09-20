package pl.khuzzuk.battles.stages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.decks.BattleSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayStage {
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private CardRepository cardRepository;
    public static PlayStage get() {
        PlayStage stage = new PlayStage();
        String setCardRepo = "setCardRepoInPlayStage";
        BUS.setReaction(setCardRepo, stage::setCardRepository);
        BUS.setReaction(Stages.BATTLE_TABLE_READY.name(), stage::startBattle);
        BUS.sendCommunicate(Container.GET_CARD_REPO.name(), setCardRepo);
        return stage;
    }

    private void startBattle(BattleSetup battleSetup) {
        BattleSetup aiBattleSetup = getAIBattleSetup(cardRepository.getAllCards());
        BUS.send(Stages.BATTLE_START_PLAYER.name(), battleSetup);
        BUS.send(Stages.BATTLE_START_OPPONENT.name(), aiBattleSetup);
    }

    public static BattleSetup getAIBattleSetup(List<Card> availableCards) {
        return BattleSetup.get(
                fillRandomly(availableCards, 3),
                fillRandomly(availableCards, 4),
                fillRandomly(availableCards, 4),
                fillRandomly(availableCards, 4));
    }

    private static List<Card> fillRandomly(List<Card> cards, int size) {
        Random random = new Random();
        List<Card> backCards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            backCards.add(cards.get(random.nextInt(cards.size())));
        }
        return backCards;
    }
}
