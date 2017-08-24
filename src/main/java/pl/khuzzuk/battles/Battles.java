package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;

public class Battles extends Application {
    //public static final Bus BUS = Bus.initializeBus(false);
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battles");
        //stage.setScene(new Scene(new Group(CardViewer.instance("file:cards/rome-back.png"))));
        DeckViewer deckViewer = DeckViewer.getInstance(1000, 800);
        deckViewer.addCard(CardViewer.instance("file:cards/rome-back.png"));
        deckViewer.addCard(CardViewer.instance("file:cards/rome-back.png"));
        stage.setScene(new Scene(deckViewer));
        stage.show();
    }
}
