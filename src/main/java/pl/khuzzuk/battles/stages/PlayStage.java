package pl.khuzzuk.battles.stages;

import lombok.*;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.messaging.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayStage {
    private final Bus BUS;
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private CardRepository cardRepository;

    public static PlayStage get(Bus bus) {
        PlayStage stage = new PlayStage(bus);
        String setCardRepo = "setCardRepoInPlayStage";
        bus.setReaction(setCardRepo, stage::setCardRepository);
        bus.setReaction(Stages.BATTLE_TABLE_READY.name(), stage::startBattle);
        bus.sendCommunicate(Container.GET_CARD_REPO.name(), setCardRepo);
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

    private static Deck fillRandomly(List<Card> cards, int size) {
        Random random = new Random();
        List<Card> backCards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            backCards.add(cards.get(random.nextInt(cards.size())));
        }
        return Deck.get(backCards);
    }
}
