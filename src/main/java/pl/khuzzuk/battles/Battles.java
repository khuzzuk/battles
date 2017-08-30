package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardStyle;
import pl.khuzzuk.battles.model.Speed;
import pl.khuzzuk.battles.ui.PlayViewer;

public class Battles extends Application {
    //public static final Bus BUS = Bus.initializeBus(false);
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
        PlayViewer playViewer = PlayViewer.get((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight());
        CardStyle romeStyle = CardStyle.builder().backgroundPath("file:cards/rome-back.png").build();
        Card card = Card.builder()
                .defence(3)
                .speed(Speed.SLOW)
                .strength(3)
                .cost(6)
                .style(romeStyle).build();
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.addCardToDeck(card);
        playViewer.showDeck();
        root.getChildren().add(playViewer);
    }
}
