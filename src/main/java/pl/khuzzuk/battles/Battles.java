package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.ui.BattleSetupViewer;
import pl.khuzzuk.messaging.Bus;

public class Battles extends Application {
    public static final Bus BUS = Bus.initializeBus(false);
    public static void main(String[] args) {
        ObjectContainer.putBeans();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battles");
        stage.setMaximized(true);
        Group root = new Group();
        stage.setScene(new Scene(root));
        stage.show();
        BattleSetupViewer battleSetupViewer = BattleSetupViewer.get((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight());
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
        root.getChildren().add(battleSetupViewer);
    }
}
