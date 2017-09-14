package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.EventTypes.Stages;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.ui.BattleSetupViewer;
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
        int width = (int) stage.getScene().getWidth();
        int height = (int) stage.getScene().getHeight();
        BattleSetupViewer battleSetupViewer = BattleSetupViewer.get(width, height);
        String cardRepoTopic = "getCardRepoForInitialAppSetup";
        BUS.<CardRepository>setGuiReaction(cardRepoTopic, cardRepository -> {
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_P_CORNUTI));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITATENSES_V_MACEDONIA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITATENSES_V_MACEDONIA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITATENSES_V_MACEDONIA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITATENSES_V_MACEDONIA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITATENSES_V_MACEDONIA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.CATAPHRACTARII));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.EQUITES_DALMATAE));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.AUX_PALATINA));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.COMITES_CLIBANARII));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.EQUITES_SAGITARII));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.CLIBANARII_PARTHII));
            battleSetupViewer.addCardToDeck(cardRepository.getCard(CardRepository.CLIBANARII));
            battleSetupViewer.showDeck();
        });
        BUS.sendCommunicate(Container.GET_CARD_REPO, cardRepoTopic);
        BUS.setGuiReaction(Stages.FORMATION_READY, battleSetup -> {
            root.getChildren().clear();
            root.getChildren().add(BattleView.get(width, height));
            BUS.send(Stages.BATTLE_TABLE_READY, battleSetup);
        });
        root.getChildren().add(battleSetupViewer);
    }
}
