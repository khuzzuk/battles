package pl.khuzzuk.battles.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.ui.decorators.CardDecoratorManager;
import pl.khuzzuk.battles.ui.decorators.FocusableDecorator;
import pl.khuzzuk.battles.ui.decorators.SelectableDecorator;
import pl.khuzzuk.messaging.Bus;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DamageViewer extends PositionablePane {
    private DeckViewer playerDeckViewer;
    private DeckViewer opponentDeckViewer;

    static DamageViewer create(double startingWidth, double startingHeight) {
        DamageViewer damageViewer = new DamageViewer();
        damageViewer.setWidth(startingWidth);
        damageViewer.setHeight(startingHeight);
        damageViewer.setupBack();
        return damageViewer;
    }

    private void setupBack() {
        Rectangle backElement = new Rectangle(getWidth(), getHeight());
        backElement.setStrokeWidth(0);
        backElement.setFill(new Color(0.01, 0.01, 0.01, 0.5));
        positionElement(backElement, 0d, 0d);
        MenuManager menuManager = MenuManager.get();
        menuManager.addButton("Resolve", true, getChildren());
    }

    void setupDecks(Deck playerDeck, Deck opponentDeck, Bus bus, Predicate<Card> decoratorFilter) {
        clear();
        int deckHeight = (int) ((getHeight() - MenuManager.MENU_HEIGHT) / 2);

        playerDeckViewer = DeckViewer.get((int) getWidth(), deckHeight, CardDecoratorManager.get()
                .addFiltered(decoratorFilter, new SelectableDecorator(EventTypes.User.SELECT_CARD, bus))
                .addUnfiltered(new FocusableDecorator()));
        opponentDeckViewer = DeckViewer.get((int) getWidth(), deckHeight, CardDecoratorManager.get()
                .addUnfiltered(new SelectableDecorator(EventTypes.User.SELECT_OPPONENT_CARD, bus))
                .addUnfiltered(new FocusableDecorator()));

        combineDecks(playerDeck, playerDeckViewer);
        combineDecks(opponentDeck, opponentDeckViewer);
        positionElement(playerDeckViewer, 0d, getHeight() / 2 + (double) MenuManager.MENU_HEIGHT);
        positionElement(opponentDeckViewer, 0d, (double) MenuManager.MENU_HEIGHT);
        repaint();
    }

    void clear() {
        getChildren().removeAll(playerDeckViewer, opponentDeckViewer);
    }

    private void combineDecks(Deck deck, DeckViewer deckViewer) {
        deck.getCards().forEach(deckViewer::addCard);
    }

    private void repaint() {
        playerDeckViewer.repaintDeck();
        opponentDeckViewer.repaintDeck();
    }
}
