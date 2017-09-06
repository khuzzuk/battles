package pl.khuzzuk.battles.decks;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Deck {
    private List<Card> cards;

    public static Deck get(List<Card> cards) {
        Deck deck = new Deck();
        deck.cards = cards;
        return deck;
    }
}
