package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class BattleDecks extends AnchorPane {
    private DeckViewer leftDeck;
    private DeckViewer centerDeck;
    private DeckViewer rightDeck;

    static BattleDecks get(double width, double height) {
        BattleDecks decks = new BattleDecks();
        decks.setWidth(width);
        decks.setHeight(height);
        decks.setupDecks();
        return decks;
    }

    private void setupDecks() {
        leftDeck = getDeckViewer(0);
        centerDeck = getDeckViewer(1);
        rightDeck = getDeckViewer(2);
        getChildren().addAll(getRectangle(0), getRectangle(1), getRectangle(2),
                leftDeck, centerDeck, rightDeck);
    }

    private Rectangle getRectangle(int fromLeft) {
        Rectangle rectangle = new Rectangle(getWidth() / 3, getHeight());
        AnchorPane.setLeftAnchor(rectangle, getWidth() / 3 * fromLeft);
        AnchorPane.setTopAnchor(rectangle, 0d);
        return rectangle;
    }

    private DeckViewer getDeckViewer(int fromLeft) {
        DeckViewer deckViewer = DeckViewer.getInstance((int) getWidth() / 3, (int) getHeight());
        AnchorPane.setLeftAnchor(deckViewer, getWidth() / 3 * fromLeft);
        AnchorPane.setTopAnchor(deckViewer, 0d);
        return deckViewer;
    }

    void drop(CardViewer cardViewer, double x) {
        double v = getWidth() / 3;
        if (x < v) {
            leftDeck.addCard(cardViewer, x);
            leftDeck.repaintDeck();
        } else if (x < v * 2) {
            centerDeck.addCard(cardViewer, x - v);
            centerDeck.repaintDeck();
        } else {
            rightDeck.addCard(cardViewer, x - v * 2);
            rightDeck.repaintDeck();
        }
    }

    void addToLeft(Card card) {
        leftDeck.addCard(card);
    }

    void addToCenter(Card card) {
        centerDeck.addCard(card);
    }

    void addToRight(Card card) {
        rightDeck.addCard(card);
    }

    void removeCard(CardViewer cardViewer) {
        leftDeck.removeCard(cardViewer);
        centerDeck.removeCard(cardViewer);
        rightDeck.removeCard(cardViewer);
    }

    void repaintDecks() {
        leftDeck.repaintDeck();
        centerDeck.repaintDeck();
        rightDeck.repaintDeck();
    }

    int size() {
        return leftDeck.size() + centerDeck.size() + rightDeck.size();
    }

    List<Card> getLeftDeck() {
        return leftDeck.getCardsFromDeck();
    }

    List<Card> getCenterDeck() {
        return centerDeck.getCardsFromDeck();
    }

    List<Card> getRightDeck() {
        return rightDeck.getCardsFromDeck();
    }
}
