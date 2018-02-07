package pl.khuzzuk.battles.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardRepository;

public class CardViewers extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battles");
        stage.setMaximized(true);
        Group root = new Group();
        stage.setScene(new Scene(root));
        stage.show();

        CardRepository cardRepository = CardRepository.create();
        Card card = cardRepository.getAllCards().get(0);
        FlowPane flowPane = new FlowPane();
        flowPane.setMinWidth(1920);
        flowPane.getChildren().addAll(
                CardViewer.instance(card, 200),
                CardViewer.instance(card, 400),
                CardViewer.instance(card, 600),
                CardViewer.instance(card, 800)
        );
        root.getChildren().add(flowPane);
    }
}
