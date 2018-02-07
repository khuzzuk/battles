package pl.khuzzuk.battles;

import groovy.transform.CompileStatic;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.stages.PlayStage;
import pl.khuzzuk.battles.ui.BattleView;
import pl.khuzzuk.messaging.Bus;

@CompileStatic
public class DamageStageSpec extends Application {
    private Bus BUS = Bus.initializeBus(true);

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ObjectContainer.putBeans(BUS);

        stage.setTitle("Battles");
        stage.setOnCloseRequest(event -> BUS.closeBus());
        stage.setMaximized(true);
        Group root = new Group();
        stage.setScene(new Scene(root));
        stage.show();

        CardRepository cardRepository = CardRepository.create();

        int width = (int) stage.getScene().getWidth();
        int height = (int) stage.getScene().getHeight();
        root.getChildren().clear();
        root.getChildren().add(BattleView.get(width, height, BUS));
        BUS.send(EventTypes.Stages.BATTLE_TABLE_READY.name(), PlayStage.getAIBattleSetup(cardRepository.getAllCards()));
    }
}
