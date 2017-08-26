package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckViewer extends AnchorPane implements Focusable {
    private List<CardViewer> deck;
    private int frameSize;
    public static DeckViewer getInstance(int windowWidth, int windowHeight) {
        DeckViewer viewer = new DeckViewer();
        viewer.setMaxWidth(windowWidth * 0.9);
        viewer.setMaxHeight(windowHeight / 3);
        viewer.frameSize = (int) (viewer.getWidth() / 10);
        viewer.deck = new ArrayList<>();
        return viewer;
    }

    public void addCard(Card card) {
        CardViewer viewer = CardViewer.instance(card);
        deck.add(viewer);
    }

    public void repaintDeck() {
        getChildren().clear();
        int space = (int) (getMaxWidth() / deck.size() - frameSize);
        for (int i = 0; i < deck.size(); i++) {
            AnchorPane.setLeftAnchor(deck.get(i), (double) (space * i + frameSize));
            setFocusAnimation(deck.get(i));
            getChildren().add(deck.get(i));
        }
    }
}
