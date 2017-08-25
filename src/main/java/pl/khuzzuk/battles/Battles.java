package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardStyle;
import pl.khuzzuk.battles.model.Speed;
import pl.khuzzuk.battles.ui.DeckViewer;

public class Battles extends Application {
    //public static final Bus BUS = Bus.initializeBus(false);
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battles");
        DeckViewer deckViewer = DeckViewer.getInstance(1800, 1100);
        CardStyle romeStyle = CardStyle.builder().backgroundPath("file:cards/rome-back.png").build();
        Card card = Card.builder()
                .defence(3)
                .speed(Speed.SLOW)
                .strength(3)
                .cost(6)
                .style(romeStyle).build();
        deckViewer.addCard(card);
        deckViewer.addCard(card);
        deckViewer.addCard(card);
        deckViewer.addCard(card);
        deckViewer.repaintDeck();
        stage.setScene(new Scene(deckViewer));
        stage.show();
    }
}
