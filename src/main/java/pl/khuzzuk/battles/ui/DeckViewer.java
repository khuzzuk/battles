package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DeckViewer extends AnchorPane implements Focusable {
    private List<CardViewer> deck;
    private int frameSize;
    static DeckViewer getInstance(int windowWidth, int windowHeight) {
        DeckViewer viewer = new DeckViewer();
        viewer.setMaxWidth(windowWidth * 0.9);
        viewer.setMaxHeight(windowHeight);
        viewer.frameSize = (int) (viewer.getWidth() / 10);
        viewer.deck = new ArrayList<>();
        return viewer;
    }

    void addCard(CardViewer cardViewer) {
        deck.add(cardViewer);
    }

    CardViewer addCard(Card card) {
        CardViewer viewer = CardViewer.instance(card, (int) getMaxHeight());
        deck.add(viewer);
        return viewer;
    }

    void removeCard(CardViewer cardViewer) {
        getChildren().removeAll(cardViewer);
        deck.remove(cardViewer);
    }

    void repaintDeck() {
        getChildren().clear();
        int space = (int) (getMaxWidth() / deck.size() - frameSize);
        for (int i = 0; i < deck.size(); i++) {
            AnchorPane.setLeftAnchor(deck.get(i), (double) (space * i + frameSize));
            AnchorPane.setTopAnchor(deck.get(i), 0d);
            setFocusAnimation(deck.get(i));
            getChildren().add(deck.get(i));
        }
    }
}
