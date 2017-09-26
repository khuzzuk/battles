package pl.khuzzuk.battles.ui;

import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.ui.decorators.CardDecoratorManager;
import pl.khuzzuk.battles.ui.decorators.FocusableDecorator;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class BattleDecks extends PositionablePane {
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
        leftDeck = addDeck(0);
        centerDeck = addDeck(1);
        rightDeck = addDeck(2);
    }

    private DeckViewer addDeck(int fromLeft) {
        double leftAnchor = getWidth() / 3 * fromLeft;
        positionElement(new Rectangle(getWidth() / 3, getHeight()), leftAnchor, 0d);
        DeckViewer deckViewer = DeckViewer.get((int) getWidth() / 3, (int) getHeight(), CardDecoratorManager.get().addUnfiltered(new FocusableDecorator()));
        positionElement(deckViewer,
                leftAnchor, 0d);
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

    void clear() {
        leftDeck.clear();
        centerDeck.clear();
        rightDeck.clear();
    }
}
