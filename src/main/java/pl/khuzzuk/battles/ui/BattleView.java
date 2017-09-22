package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.EventTypes;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.messaging.Bus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleView extends PositionablePane {
    @NonNull
    private Bus bus;
    private Button nextRoundButton;
    private MenuManager menuManager;
    private double deckHeight;
    private BattleSetup playerSetup;
    private double damageViewerBorder;
    private DamageViewer leftDamageViewer;
    private BattleSetup opponentSetup;

    public static BattleView get(int width, int height, Bus bus) {
        BattleView battleView = new BattleView(bus);
        battleView.bus = bus;
        battleView.setWidth(width);
        battleView.setMinWidth(width);
        battleView.setMinHeight(height);
        battleView.damageViewerBorder = width * 0.05;
        battleView.menuManager = MenuManager.get();
        battleView.deckHeight = (height - battleView.menuManager.menuHeight) / 3d;
        bus.setGuiReaction(Stages.BATTLE_START_PLAYER.name(), battleView::setPlayerSetup);
        bus.setGuiReaction(Stages.BATTLE_START_OPPONENT.name(), battleView::setOpponentSetup);
        battleView.setupMenu();
        return battleView;
    }

    private void setupMenu() {
        nextRoundButton = menuManager.addButton("Next round", true, getChildren());
    }

    private void setPlayerSetup(BattleSetup playerSetup) {
        this.playerSetup = playerSetup;

        BattleDecks playerBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(playerBattleDecks, playerSetup);
        positionElement(playerBattleDecks, 0d, menuManager.menuHeight + deckHeight);
        playerBattleDecks.repaintDecks();
        playerBattleDecks.setLeftDeckOnAction(showLeftDamageViewerEventHandler());

        DeckViewer playersBack = DeckViewer.get((int) getWidth(), (int) deckHeight);
        playerSetup.getBack().forEach(playersBack::addCard);
        playersBack.repaintDeck();
        positionElement(playersBack, 0d, menuManager.menuHeight + deckHeight * 2);
    }

    private void setOpponentSetup(BattleSetup opponentSetup) {
        this.opponentSetup = opponentSetup;
        BattleDecks opponentBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(opponentBattleDecks, opponentSetup);
        AnchorPane.setLeftAnchor(opponentBattleDecks, 0d);
        AnchorPane.setTopAnchor(opponentBattleDecks, (double) menuManager.menuHeight);
        opponentBattleDecks.repaintDecks();
        getChildren().add(opponentBattleDecks);
        opponentBattleDecks.toBack();
    }

    private void fillBattleDecksViewer(BattleDecks decks, BattleSetup setup) {
        setup.getLeft().forEach(decks::addToLeft);
        setup.getCenter().forEach(decks::addToCenter);
        setup.getRight().forEach(decks::addToRight);
    }

    private Runnable showLeftDamageViewerEventHandler() {
        DeckViewer playerLeftDeck = SelectableDeckViewer.get(
                getWidth() - damageViewerBorder,
                (getHeight() - damageViewerBorder) / 2, bus, EventTypes.User.SELECT_CARD);

        DeckViewer opponentLeftDeck = SelectableDeckViewer.get(
                getWidth() - damageViewerBorder,
                (getHeight() - damageViewerBorder) / 2, bus, EventTypes.User.SELECT_OPPONENT_CARD);

        leftDamageViewer = DamageViewer.get(getWidth() - damageViewerBorder,
                getHeight() - damageViewerBorder, playerLeftDeck, opponentLeftDeck);
        leftDamageViewer.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                getChildren().removeAll(leftDamageViewer);
            }
        });

        return () -> {
            opponentLeftDeck.clear();
            opponentSetup.getLeft().forEach(opponentLeftDeck::addCard);
            opponentLeftDeck.repaintDeck();

            playerLeftDeck.clear();
            playerSetup.getLeft().forEach(playerLeftDeck::addCard);
            playerLeftDeck.repaintDeck();

            getChildren().removeAll(leftDamageViewer);
            positionElement(leftDamageViewer, damageViewerBorder / 2, damageViewerBorder / 2);
        };
    }
}
