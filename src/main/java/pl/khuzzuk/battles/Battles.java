package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.stages.PlayStage;
import pl.khuzzuk.battles.ui.BattleView;
import pl.khuzzuk.messaging.Bus;

public class Battles extends Application {
    public static final Bus BUS = Bus.initializeBus(true);

    public static void main(String[] args) {
        ObjectContainer.putBeans();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battles");
        stage.setOnCloseRequest(event -> BUS.closeBus());
        stage.setMaximized(true);
        Group root = new Group();
        stage.setScene(new Scene(root));
        stage.show();

        //TODO remove Card Repo from here, just to hack
        CardRepository cardRepository = CardRepository.get();

        int width = (int) stage.getScene().getWidth();
        int height = (int) stage.getScene().getHeight();
        root.getChildren().clear();
        root.getChildren().add(BattleView.get(width, height));
        BUS.send(Stages.BATTLE_TABLE_READY.name(), PlayStage.getAIBattleSetup(cardRepository.getAllCards()));
    }
}
