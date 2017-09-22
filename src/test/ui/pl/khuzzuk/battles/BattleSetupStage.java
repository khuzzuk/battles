package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.ui.BattleSetupViewer;
import pl.khuzzuk.battles.ui.BattleView;
import pl.khuzzuk.messaging.Bus;

public class BattleSetupStage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Bus bus = Bus.initializeBus(true);
        ObjectContainer.putBeans(bus);

        stage.setTitle("Battles");
        stage.setOnCloseRequest(event -> bus.closeBus());
        stage.setMaximized(true);
        Group root = new Group();
        stage.setScene(new Scene(root));
        stage.show();

        int width = (int) stage.getScene().getWidth();
        int height = (int) stage.getScene().getHeight();
        BattleSetupViewer battleSetupViewer = BattleSetupViewer.get(width, height, bus);
        String cardRepoTopic = "getCardRepoForInitialAppSetup";
        bus.<CardRepository>setGuiReaction(cardRepoTopic, cardRepo -> {
            cardRepo.getAllCards().forEach(battleSetupViewer::addCardToDeck);
            battleSetupViewer.showDeck();
        });
        bus.sendCommunicate(EventTypes.Container.GET_CARD_REPO.name(), cardRepoTopic);
        bus.setGuiReaction(EventTypes.Stages.FORMATION_READY.name(), battleSetup -> {
            root.getChildren().clear();
            root.getChildren().add(BattleView.get(width, height, bus));
            bus.send(EventTypes.Stages.BATTLE_TABLE_READY.name(), battleSetup);
        });
        root.getChildren().add(battleSetupViewer);

    }
}
