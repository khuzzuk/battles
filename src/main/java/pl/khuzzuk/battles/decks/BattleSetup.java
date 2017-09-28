package pl.khuzzuk.battles.decks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BattleSetup {
    private Deck back;
    private Deck left;
    private Deck center;
    private Deck right;

    public static BattleSetup get(Deck back,
                                  Deck left,
                                  Deck center,
                                  Deck right) {
        BattleSetup setup = new BattleSetup();
        setup.back = back;
        setup.left = left;
        setup.center = center;
        setup.right = right;
        return setup;
    }

    public List<Card> getAllCardsInPlay() {
        List<Card> cards = new ArrayList<>(left.getCards());
        cards.addAll(center.getCards());
        cards.addAll(right.getCards());
        return cards;
    }
}
