package pl.khuzzuk.battles.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.messaging.Bus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DamageViewer extends PositionablePane {
    private DeckViewer playerDeckViewer;
    private DeckViewer opponentDeckViewer;

    static DamageViewer get(double startingWidth, double startingHeight,
                            Deck playerDeck, Deck opponentDeck, Bus bus) {
        DamageViewer damageViewer = new DamageViewer();
        damageViewer.setWidth(startingWidth);
        damageViewer.setHeight(startingHeight);
        damageViewer.setupBack();
        damageViewer.setupDecks(playerDeck, opponentDeck, bus);
        return damageViewer;
    }

    private void setupBack() {
        Rectangle backElement = new Rectangle(getWidth(), getHeight());
        backElement.setStrokeWidth(0);
        backElement.setFill(new Color(0.01, 0.01, 0.01, 0.5));
        positionElement(backElement, 0d, 0d);
    }

    private void setupDecks(Deck playerDeck, Deck opponentDeck, Bus bus) {
        MenuManager menuManager = MenuManager.get();
        menuManager.addButton("Resolve", true, getChildren());
        int deckHeight = (int) ((getHeight() - MenuManager.menuHeight) / 2);
        playerDeckViewer = SelectableDeckViewer.get((int) getWidth(), deckHeight, bus, EventTypes.User.SELECT_CARD);
        opponentDeckViewer = SelectableDeckViewer.get((int) getWidth(), deckHeight, bus, EventTypes.User.SELECT_OPPONENT_CARD);
        combineDecks(playerDeck, playerDeckViewer);
        combineDecks(opponentDeck, opponentDeckViewer);
        positionElement(playerDeckViewer, 0d, (double) MenuManager.menuHeight);
        positionElement(opponentDeckViewer, 0d, getHeight() / 2 + (double) MenuManager.menuHeight);
    }

    void clear() {
        playerDeckViewer.clear();
        opponentDeckViewer.clear();
    }

    private void combineDecks(Deck deck, DeckViewer deckViewer) {
        deck.getCards().forEach(deckViewer::addCard);
    }

    void addPlayerCard(Card card) {
        playerDeckViewer.addCard(card);
    }

    void addOpponentCard(Card card) {
        opponentDeckViewer.addCard(card);
    }

    void repaint() {
        playerDeckViewer.repaintDeck();
        opponentDeckViewer.repaintDeck();
    }
}
