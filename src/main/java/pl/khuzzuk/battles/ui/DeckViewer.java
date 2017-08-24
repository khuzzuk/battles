package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckViewer extends AnchorPane {
    private List<CardViewer> deck;
    private int frameSize;
    public static DeckViewer getInstance(int windowWidth, int windowHeight) {
        DeckViewer viewer = new DeckViewer();
        viewer.setWidth(windowWidth * 0.9);
        viewer.setHeight(windowHeight / 3);
        viewer.frameSize = (int) (viewer.getWidth() / 10);
        viewer.deck = new ArrayList<>();
        return viewer;
    }

    public void addCard(CardViewer viewer) {
        deck.add(viewer);
        repaintDeck();
    }

    private void repaintDeck() {
        getChildren().clear();
        int space = (int) (getWidth() / deck.size() - frameSize);
        for (int i = 0; i < deck.size(); i++) {
            AnchorPane.setLeftAnchor(deck.get(i), (double) (space * (i + 1) + frameSize));
            getChildren().add(deck.get(i));
        }
    }
}
