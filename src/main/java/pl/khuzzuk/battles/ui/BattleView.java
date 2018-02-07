package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.decks.BattleSetup;
import pl.khuzzuk.battles.decks.Deck;
import pl.khuzzuk.battles.ui.decorators.CardDecoratorManager;
import pl.khuzzuk.battles.ui.decorators.FocusableDecorator;
import pl.khuzzuk.functions.MultiGate;
import pl.khuzzuk.messaging.Bus;

import java.util.function.Predicate;

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
        battleView.setHeight(height);
        battleView.setMinHeight(height);
        battleView.damageViewerBorder = width * 0.05;
        battleView.menuManager = MenuManager.get();
        battleView.deckHeight = (height - MenuManager.MENU_HEIGHT) / 3d;
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
            playerBattleDecks.setLeftDeckOnAction(showDamageViewerEventHandler(playerSetup.getLeft(), opponentSetup.getLeft(),
                    Stages.GET_DAMAGE_STAGE_LEFT, Stages.SHOW_DAMAGE_STAGE_LEFT));
            playerBattleDecks.setCenterDeckOnAction(showDamageViewerEventHandler(playerSetup.getCenter(), opponentSetup.getCenter(),
                    Stages.GET_DAMAGE_STAGE_CENTER, Stages.SHOW_DAMAGE_STAGE_CENTER));
            playerBattleDecks.setRightDeckOnAction(showDamageViewerEventHandler(playerSetup.getRight(), opponentSetup.getRight(),
                    Stages.GET_DAMAGE_STAGE_RIGHT, Stages.SHOW_DAMAGE_STAGE_RIGHT));
        }, () -> {});
    }

    private void setPlayerSetup(BattleSetup playerSetup) {
        this.playerSetup = playerSetup;

        playerBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(playerBattleDecks, playerSetup);
        positionElement(playerBattleDecks, 0d, MenuManager.MENU_HEIGHT + deckHeight);
        playerBattleDecks.repaintDecks();

        DeckViewer playersBack = DeckViewer.get((int) getWidth(), (int) deckHeight, CardDecoratorManager.get().addUnfiltered(new FocusableDecorator()));
        playerSetup.getBack().getCards().forEach(playersBack::addCard);
        playersBack.repaintDeck();
        positionElement(playersBack, 0d, MenuManager.MENU_HEIGHT + deckHeight * 2);
        battleReady.on(0);
    }

    private void setOpponentSetup(BattleSetup opponentSetup) {
        this.opponentSetup = opponentSetup;
        opponentBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(opponentBattleDecks, opponentSetup);
        AnchorPane.setLeftAnchor(opponentBattleDecks, 0d);
        AnchorPane.setTopAnchor(opponentBattleDecks, (double) MenuManager.MENU_HEIGHT);
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

    private Runnable showDamageViewerEventHandler(Deck playerSetupDeck, Deck opponentSetupDeck, Enum<?> requestType, Enum<?> responseType) {
        DamageViewer damageViewer = DamageViewer.create(getWidth() - damageViewerBorder,
                getHeight() - damageViewerBorder);
        damageViewer.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                getChildren().removeAll(damageViewer);
            }
        });

        bus.<Predicate<Card>>setGuiReaction(responseType.name(),
                filter -> damageViewer.setupDecks(playerSetupDeck, opponentSetupDeck, bus, filter));

        return () -> {
            damageViewer.clear();
            getChildren().removeAll(damageViewer);
            positionElement(damageViewer, damageViewerBorder / 2, damageViewerBorder / 2);
            bus.sendCommunicate(requestType.name(), responseType.name());
        };
    }
}
