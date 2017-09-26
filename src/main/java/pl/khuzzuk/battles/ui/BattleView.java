package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.functions.MultiGate;
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
    private BattleSetup opponentSetup;
    private MultiGate battleReady;
    private BattleDecks playerBattleDecks;
    private BattleDecks opponentBattleDecks;

    public static BattleView get(int width, int height, Bus bus) {
        BattleView battleView = new BattleView(bus);
        battleView.bus = bus;
        battleView.setWidth(width);
        battleView.setMinWidth(width);
        battleView.setMinHeight(height);
        battleView.damageViewerBorder = width * 0.05;
        battleView.menuManager = MenuManager.get();
        battleView.deckHeight = (height - battleView.menuManager.menuHeight) / 3d;
        battleView.setupDamageViewers();
        bus.setGuiReaction(Stages.BATTLE_START_PLAYER.name(), battleView::setPlayerSetup);
        bus.setGuiReaction(Stages.BATTLE_START_OPPONENT.name(), battleView::setOpponentSetup);
        battleView.setupMenu();
        return battleView;
    }

    private void setupMenu() {
        nextRoundButton = menuManager.addButton("Next round", true, getChildren());
    }

    private void setupDamageViewers() {
        battleReady = MultiGate.of(2, () -> {
            playerBattleDecks.setLeftDeckOnAction(showDamageViewerEventHandler(playerSetup.getLeft(), opponentSetup.getLeft()));
            playerBattleDecks.setCenterDeckOnAction(showDamageViewerEventHandler(playerSetup.getCenter(), opponentSetup.getCenter()));
            playerBattleDecks.setRightDeckOnAction(showDamageViewerEventHandler(playerSetup.getRight(), opponentSetup.getRight()));
        }, () -> {});
    }

    private void setPlayerSetup(BattleSetup playerSetup) {
        this.playerSetup = playerSetup;

        playerBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(playerBattleDecks, playerSetup);
        positionElement(playerBattleDecks, 0d, menuManager.menuHeight + deckHeight);
        playerBattleDecks.repaintDecks();

        DeckViewer playersBack = DeckViewer.get((int) getWidth(), (int) deckHeight);
        playerSetup.getBack().getCards().forEach(playersBack::addCard);
        playersBack.repaintDeck();
        positionElement(playersBack, 0d, menuManager.menuHeight + deckHeight * 2);
        battleReady.on(0);
    }

    private void setOpponentSetup(BattleSetup opponentSetup) {
        this.opponentSetup = opponentSetup;
        opponentBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(opponentBattleDecks, opponentSetup);
        AnchorPane.setLeftAnchor(opponentBattleDecks, 0d);
        AnchorPane.setTopAnchor(opponentBattleDecks, (double) menuManager.menuHeight);
        opponentBattleDecks.repaintDecks();
        getChildren().add(opponentBattleDecks);
        opponentBattleDecks.toBack();
        battleReady.on(1);
    }

    private void fillBattleDecksViewer(BattleDecks decks, BattleSetup setup) {
        setup.getLeft().getCards().forEach(decks::addToLeft);
        setup.getCenter().getCards().forEach(decks::addToCenter);
        setup.getRight().getCards().forEach(decks::addToRight);
    }

    private Runnable showDamageViewerEventHandler(Deck playerSetupDeck, Deck opponentSetupDeck) {
        DamageViewer damageViewer = DamageViewer.get(getWidth() - damageViewerBorder,
                getHeight() - damageViewerBorder, playerSetupDeck, opponentSetupDeck, bus);
        damageViewer.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                getChildren().removeAll(damageViewer);
            }
        });

        return () -> {
            damageViewer.clear();

            opponentSetupDeck.getCards().forEach(damageViewer::addOpponentCard);
            playerSetupDeck.getCards().forEach(damageViewer::addPlayerCard);
            damageViewer.repaint();

            getChildren().removeAll(damageViewer);
            positionElement(damageViewer, damageViewerBorder / 2, damageViewerBorder / 2);
        };
    }
}
