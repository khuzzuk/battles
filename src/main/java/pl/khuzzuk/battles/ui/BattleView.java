package pl.khuzzuk.battles.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.decks.BattleSetup;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleView extends AnchorPane {
    private Button nextRoundButton;
    private MenuManager menuManager;
    private double deckHeight;

    public static BattleView get(int width, int height) {
        BattleView battleView = new BattleView();
        battleView.setWidth(width);
        battleView.setMinWidth(width);
        battleView.setHeight(height);
        battleView.menuManager = MenuManager.get();
        battleView.deckHeight = (height - battleView.menuManager.menuHeight) / 3d;
        BUS.setGuiReaction(Stages.BATTLE_START_PLAYER, battleView::setPlayerSetup);
        BUS.setGuiReaction(Stages.BATTLE_START_OPPONENT, battleView::setOpponentSetup);
        battleView.setupMenu();
        return battleView;
    }

    private void setupMenu() {
        nextRoundButton = menuManager.addButton("Next round", true, getChildren());
    }

    private void setPlayerSetup(BattleSetup playerSetup) {
        BattleDecks playerBattleDecks = BattleDecks.get(getWidth(), deckHeight);
        fillBattleDecksViewer(playerBattleDecks, playerSetup);
        AnchorPane.setLeftAnchor(playerBattleDecks, 0d);
        AnchorPane.setTopAnchor(playerBattleDecks, menuManager.menuHeight + deckHeight);
        playerBattleDecks.repaintDecks();

        DeckViewer playersBack = DeckViewer.get((int) getWidth(), (int) deckHeight);
        playerSetup.getBack().forEach(playersBack::addCard);
        playersBack.repaintDeck();
        AnchorPane.setLeftAnchor(playersBack, 0d);
        AnchorPane.setTopAnchor(playersBack, menuManager.menuHeight + deckHeight * 2);

        getChildren().addAll(playerBattleDecks, playersBack);
    }

    private void setOpponentSetup(BattleSetup opponentSetup) {
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
}
