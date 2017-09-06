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
        viewer.setOnMouseExited(event -> viewer.deck.forEach(CardViewer::toBack));
        return viewer;
    }

    void addCard(CardViewer cardViewer, double x) {
        int size = deck.size();
        deck.remove(cardViewer);
        double width = getWidth() / size;
        for (int i = 0; i < size; i++) {
            if (width * i < x && width * (i + 1) > x) {
                deck.add(i, cardViewer);
                return;
            }
        }
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
            setFocusAnimation(deck.get(i), this::addOnTop);
            getChildren().add(deck.get(i));
        }
    }

    private void addOnTop(CardViewer viewer) {
        deck.forEach(CardViewer::toBack);
        viewer.toFront();
    }

    int size() {
        return deck.size();
    }
}
