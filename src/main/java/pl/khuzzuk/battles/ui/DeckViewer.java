package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckViewer extends AnchorPane {
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
        deck.add(CardViewer.instance(card));
    }

    public void repaintDeck() {
        getChildren().clear();
        int space = (int) (getMaxWidth() / deck.size() - frameSize);
        for (int i = 0; i < deck.size(); i++) {
            AnchorPane.setLeftAnchor(deck.get(i), (double) (space * i + frameSize));
            AnchorPane.setRightAnchor(deck.get(i), (double) frameSize);
            getChildren().add(deck.get(i));
        }
    }
}
