package pl.khuzzuk.battles.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.functions.Calculations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class DeckViewer extends PositionablePane {
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
        deck.add(Calculations.middle(0, (int) Math.round(x / (getWidth() / size)), size - 1), cardViewer);
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
        AtomicInteger counter = new AtomicInteger();
        deck.forEach(cardViewer ->
                positionElement(cardViewer, space * counter.getAndIncrement() + (double) frameSize, 0d));
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
