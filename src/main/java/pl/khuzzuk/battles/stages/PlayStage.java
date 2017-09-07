package pl.khuzzuk.battles.stages;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.decks.BattleSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pl.khuzzuk.battles.Battles.BUS;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayStage {
    private final CardRepository cardRepository;
    public static PlayStage get(CardRepository cardRepository) {
        PlayStage stage = new PlayStage(cardRepository);
        BUS.setReaction(Stages.FORMATION_READY, stage::startBattle);
        return stage;
    }

    private void startBattle(BattleSetup battleSetup) {
        BattleSetup aiBattleSetup = getAIBattleSetup(30, cardRepository.getAllCards());
        BUS.send(Stages.BATTLE_START_PLAYER, battleSetup);
        BUS.send(Stages.BATTLE_START_OPPONENT, aiBattleSetup);
    }

    private BattleSetup getAIBattleSetup(int value, List<Card> availableCards) {
        return BattleSetup.get(
                fillRandomly(availableCards, 3),
                fillRandomly(availableCards, 4),
                fillRandomly(availableCards, 4),
                fillRandomly(availableCards, 4));
    }

    private List<Card> fillRandomly(List<Card> cards, int size) {
        Random random = new Random(cards.size() - 1);
        List<Card> backCards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            backCards.add(cards.get(random.nextInt()));
        }
        return backCards;
    }
}
