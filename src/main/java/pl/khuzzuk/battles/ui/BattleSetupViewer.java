package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.ui.decorators.CardDecoratorManager;
import pl.khuzzuk.battles.ui.decorators.FocusableDecorator;
import pl.khuzzuk.messaging.Bus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleSetupViewer extends PositionablePane {
    @NonNull
    private Bus bus;
    private DeckViewer deck;
    private BattleDecks battleDeck;
    private double deckHeight;
    private Button startButton;
    private MenuManager menuManager;

    public static BattleSetupViewer get(int width, int height, Bus bus) {
        BattleSetupViewer viewer = new BattleSetupViewer(bus);
        viewer.setWidth(width);
        viewer.setHeight(height);
        viewer.menuManager = MenuManager.get();
        viewer.deckHeight = (height - viewer.menuManager.menuHeight) / 2d;
        viewer.setupMenu();
        viewer.setupPlayerTable();
        viewer.setupDeckViewer();
        return viewer;
    }

    private void setupMenu() {
        startButton = menuManager.addButton("Start battle", true, getChildren());
        startButton.setOnAction(event -> toBattleStage());
    }

    private void setupDeckViewer() {
        Rectangle deckArea = new Rectangle(getWidth(), deckHeight);
        deck = DeckViewer.get((int) getWidth(), (int) deckHeight, CardDecoratorManager.get().addUnfiltered(new FocusableDecorator()));
        double topAnchor = deckHeight + MenuManager.menuHeight;
        positionElement(deckArea, 0d, topAnchor);
        positionElement(deck, 0d, topAnchor);
    }

    private void setupPlayerTable() {
        battleDeck = BattleDecks.get(getWidth(), deckHeight);
        positionElement(battleDeck, 0d, MenuManager.menuHeight);
    }

    public void addCardToDeck(Card card) {
        CardViewer cardViewer = deck.addCard(card);
        cardViewer.setOnMousePressed(event -> {
            deck.removeCard(cardViewer);
            battleDeck.removeCard(cardViewer);
            positionElement(cardViewer, event.getSceneX() - cardViewer.getWidth() / 2,
                    event.getSceneY() - cardViewer.getHeight() / 2);
            deck.repaintDeck();
        });

        cardViewer.setOnMouseDragged(event -> {
            AnchorPane.setLeftAnchor(cardViewer, event.getSceneX() - cardViewer.getWidth() / 2);
            AnchorPane.setTopAnchor(cardViewer, event.getSceneY() - cardViewer.getHeight() / 2);
        });

        cardViewer.setOnMouseReleased(event -> this.drop(cardViewer, event));
    }

    private void drop(CardViewer cardViewer, MouseEvent event) {
        Double y = event.getSceneY();
        getChildren().remove(cardViewer);
        Double playerTableY = AnchorPane.getTopAnchor(battleDeck);
        if (y > playerTableY && y < playerTableY + battleDeck.getHeight()) {
            battleDeck.drop(cardViewer, event.getSceneX());
        } else {
            battleDeck.removeCard(cardViewer);
            battleDeck.repaintDecks();
            deck.addCard(cardViewer, event.getSceneX());
            deck.repaintDeck();
        }
        startButton.setDisable(!isPossibleToStart());
    }

    public void showDeck() {
        deck.repaintDeck();
    }

    private boolean isPossibleToStart() {
        int cardsOnHand = deck.size();
        int cardsOnTable = battleDeck.size();
        int cardsInPlay = cardsOnHand + cardsOnTable;
        return cardsInPlay / 2 > cardsOnHand
                && !battleDeck.getLeftDeck().getDeck().isEmpty()
                && !battleDeck.getCenterDeck().getDeck().isEmpty()
                && !battleDeck.getRightDeck().getDeck().isEmpty();
    }

    private void toBattleStage() {
        bus.send(Stages.FORMATION_READY.name(), BattleSetup.get(
                Deck.get(deck.getCardsFromDeck()),
                Deck.get(battleDeck.getLeftDeck().getCardsFromDeck()),
                Deck.get(battleDeck.getCenterDeck().getCardsFromDeck()),
                Deck.get(battleDeck.getRightDeck().getCardsFromDeck())));
    }
}
