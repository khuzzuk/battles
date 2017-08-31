package pl.khuzzuk.battles.ui;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleSetupViewer extends AnchorPane {
    private DeckViewer deck;
    private BattleDecks playerBattleDeck;

    public static BattleSetupViewer get(int width, int height) {
        BattleSetupViewer viewer = new BattleSetupViewer();
        viewer.setWidth(width);
        viewer.setHeight(height);
        viewer.setupPlayerTable();
        viewer.setupDeckViewer();
        return viewer;
    }

    private void setupDeckViewer() {
        Rectangle deckArea = new Rectangle(getWidth(), getHeight() / 4);
        deck = DeckViewer.getInstance((int) getWidth(), (int) getHeight() / 4);
        double topAnchor = getHeight() / 4 * 3;
        AnchorPane.setTopAnchor(deckArea, topAnchor);
        AnchorPane.setLeftAnchor(deckArea, 0d);
        AnchorPane.setTopAnchor(deck, topAnchor);
        AnchorPane.setLeftAnchor(deck, 0d);
        getChildren().addAll(deckArea, deck);
    }

    private void setupPlayerTable() {
        double topAnchor = getHeight() / 4;
        playerBattleDeck = BattleDecks.get(getWidth(), topAnchor);
        AnchorPane.setTopAnchor(playerBattleDeck, topAnchor * 2);
        AnchorPane.setLeftAnchor(playerBattleDeck, 0d);
        getChildren().addAll(playerBattleDeck);
    }

    public void addCardToDeck(Card card) {
        CardViewer cardViewer = deck.addCard(card);
        cardViewer.setOnMousePressed(event -> {
            deck.removeCard(cardViewer);
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
        Double playerTableY = AnchorPane.getTopAnchor(playerBattleDeck);
        if (y > playerTableY && y < playerTableY + playerBattleDeck.getHeight()) {
            playerBattleDeck.drop(cardViewer, event.getSceneX());
        } else {
            playerBattleDeck.removeCard(cardViewer);
            playerBattleDeck.repaintDecks();
            deck.addCard(cardViewer, event.getX());
            deck.repaintDeck();
        }
    }

    public void showDeck() {
        deck.repaintDeck();
    }
}
