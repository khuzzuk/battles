package pl.khuzzuk.battles.decks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Deck {
    private List<Card> cards;

    public static Deck get(List<Card> cards) {
        Deck deck = new Deck();
        deck.cards = new ArrayList<>(cards);
        return deck;
    }
}
