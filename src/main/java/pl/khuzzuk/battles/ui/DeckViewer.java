package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class DeckViewer extends AnchorPane {
    @Getter(AccessLevel.PACKAGE)
    private List<CardViewer> deck;
    @Getter(AccessLevel.PACKAGE)
    private int frameSize;
    static DeckViewer get(int windowWidth, int windowHeight) {
        DeckViewer viewer = new DeckViewer();
        viewer.setInitialValues(windowWidth, windowHeight);
        return viewer;
    }

    void setInitialValues(double width, double height) {
        setMaxWidth(width * 0.9);
        setMaxHeight(height);
        frameSize = (int) (getWidth() / 10);
        deck = new ArrayList<>();
        setOnMouseExited(event -> deck.forEach(CardViewer::toBack));
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
        viewer.setFocusToMouseMovement(this::addOnTop, 1.25);
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
            CardViewer cardViewer = deck.get(i);
            AnchorPane.setLeftAnchor(cardViewer, (double) (space * i + frameSize));
            AnchorPane.setTopAnchor(cardViewer, 0d);
            getChildren().add(cardViewer);
        }
    }

    void addOnTop(CardViewer viewer) {
        deck.forEach(CardViewer::toBack);
        viewer.toFront();
    }

    int size() {
        return deck.size();
    }

    List<Card> getCardsFromDeck() {
        return deck.stream().map(CardViewer::getCard).collect(Collectors.toList());
    }

    void clear() {
        deck.clear();
    }
}
