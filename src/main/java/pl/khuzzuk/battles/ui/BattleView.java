package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.decks.BattleSetup;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleView extends AnchorPane {
    private BattleSetup playerSetup;
    private BattleSetup opponentSetup;

    public static BattleView get() {
        BattleView battleView = new BattleView();
        BUS.setReaction(Stages.BATTLE_START_PLAYER, battleView::setPlayerSetup);
        BUS.setReaction(Stages.BATTLE_START_OPPONENT, battleView::setOpponentSetup);
        return battleView;
    }

    private void setPlayerSetup(BattleSetup playerSetup) {

    }

    private void setOpponentSetup(BattleSetup opponentSetup) {

    }
}
