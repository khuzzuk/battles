package pl.khuzzuk.battles.ui;

import javafx.scene.Node;
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

    static BattleDecks getSelectable(double width, double height) {
        BattleDecks battleDecks = new BattleDecks();
        battleDecks.setWidth(width);
        battleDecks.setHeight(height);
        battleDecks.setupSelectableDecks();
        return battleDecks;
    }

    private void setupDecks() {
        leftDeck = getDeckViewer(0);
        centerDeck = getDeckViewer(1);
        rightDeck = getDeckViewer(2);
        getChildren().addAll(getRectangle(0), getRectangle(1), getRectangle(2),
                leftDeck, centerDeck, rightDeck);
    }

    private void setupSelectableDecks() {
        leftDeck = getSelectableDeckViewer(0);
        centerDeck = getSelectableDeckViewer(1);
        rightDeck = getSelectableDeckViewer(2);
        getChildren().addAll(getRectangle(0), getRectangle(1), getRectangle(2),
                leftDeck, centerDeck, rightDeck);
    }

    private Rectangle getRectangle(int fromLeft) {
        Rectangle rectangle = new Rectangle(getWidth() / 3, getHeight());
        setAnchorPane(rectangle, fromLeft);
        return rectangle;
    }

    private DeckViewer getDeckViewer(int fromLeft) {
        DeckViewer deckViewer = DeckViewer.get((int) getWidth() / 3, (int) getHeight());
        setAnchorPane(deckViewer, fromLeft);
        return deckViewer;
    }

    private SelectableDeckViewer getSelectableDeckViewer(int fromLeft) {
        SelectableDeckViewer deckViewer = SelectableDeckViewer.get(getWidth() / 3, getHeight());
        setAnchorPane(deckViewer, fromLeft);
        return deckViewer;
    }

    private void setAnchorPane(Node node, int fromLeft) {
        AnchorPane.setLeftAnchor(node, getWidth() / 3 * fromLeft);
        AnchorPane.setTopAnchor(node, 0d);
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

    void setLeftDeckOnAction(Runnable action) {
        setDeckOnAction(leftDeck, action);
    }

    void setCenterDeckOnAction(Runnable action) {
        setDeckOnAction(centerDeck, action);
    }

    void setRightDeckOnAction(Runnable action) {
        setDeckOnAction(rightDeck, action);
    }

    private void setDeckOnAction(DeckViewer deckViewer, Runnable action) {
        deckViewer.setOnMouseClicked(event -> action.run());
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
