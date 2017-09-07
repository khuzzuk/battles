package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleSetupViewer extends AnchorPane {
    private DeckViewer deck;
    private BattleDecks battleDeck;
    private static final int menuHeight = 100;
    private double deckHeight;
    private Button startButton;

    public static BattleSetupViewer get(int width, int height) {
        BattleSetupViewer viewer = new BattleSetupViewer();
        viewer.setWidth(width);
        viewer.setHeight(height);
        viewer.deckHeight = (height - menuHeight) / 2;
        viewer.setupMenu();
        viewer.setupPlayerTable();
        viewer.setupDeckViewer();
        return viewer;
    }

    private void setupMenu() {
        startButton = new Button("Start battle");
        startButton.setDisable(true);
        AnchorPane.setTopAnchor(startButton, 10d);
        AnchorPane.setLeftAnchor(startButton, 10d);
        startButton.setOnAction(event -> toBattleStage());
        getChildren().add(startButton);
    }

    private void setupDeckViewer() {
        Rectangle deckArea = new Rectangle(getWidth(), deckHeight);
        deck = DeckViewer.getInstance((int) getWidth(), (int) deckHeight);
        double topAnchor = deckHeight + menuHeight;
        AnchorPane.setTopAnchor(deckArea, topAnchor);
        AnchorPane.setLeftAnchor(deckArea, 0d);
        AnchorPane.setTopAnchor(deck, topAnchor);
        AnchorPane.setLeftAnchor(deck, 0d);
        getChildren().addAll(deckArea, deck);
    }

    private void setupPlayerTable() {
        battleDeck = BattleDecks.get(getWidth(), deckHeight);
        AnchorPane.setTopAnchor(battleDeck, (double) menuHeight);
        AnchorPane.setLeftAnchor(battleDeck, 0d);
        getChildren().addAll(battleDeck);
    }

    public void addCardToDeck(Card card) {
        CardViewer cardViewer = deck.addCard(card);
        cardViewer.setOnMousePressed(event -> {
            deck.removeCard(cardViewer);
            battleDeck.removeCard(cardViewer);
            AnchorPane.setLeftAnchor(cardViewer, event.getSceneX() - cardViewer.getWidth() / 2);
            AnchorPane.setTopAnchor(cardViewer, event.getSceneY() - cardViewer.getHeight() / 2);
            getChildren().add(cardViewer);
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
        return cardsInPlay / 2 > cardsOnHand && battleDeck.isFormationReady();
    }

    private void toBattleStage() {
        BUS.send(Stages.FORMATION_READY, BattleSetup.get(
                deck.getCardsFromDeck(),
                battleDeck.getLeftDeck(),
                battleDeck.getCenterDeck(),
                battleDeck.getRightDeck()));
    }
}
