package pl.khuzzuk.battles;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.cards.CardStyle;
import pl.khuzzuk.battles.model.Speed;
import pl.khuzzuk.battles.ui.BattleSetupViewer;

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
        BattleSetupViewer battleSetupViewer = BattleSetupViewer.get((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight());
        CardStyle romeStyle = CardStyle.builder().backgroundPath("file:cards/rome-back.png").build();
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 001"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 002"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 003"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 004"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 005"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 006"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 007"));
        battleSetupViewer.addCardToDeck(getCard(romeStyle, "Card 008"));
        battleSetupViewer.showDeck();
        root.getChildren().add(battleSetupViewer);
    }

    private Card getCard(CardStyle style, String name) {
        return Card.builder()
                .name(name)
                .defence(3)
                .speed(Speed.SLOW)
                .strength(3)
                .cost(6)
                .style(style).build();
    }
}
