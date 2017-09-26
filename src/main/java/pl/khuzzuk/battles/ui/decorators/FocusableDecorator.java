package pl.khuzzuk.battles.ui.decorators;

import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;

public class FocusableDecorator implements CardDecorator {
    public void addBehavior(DeckViewer deckViewer, CardViewer cardViewer) {
        cardViewer.setFocusToMouseMovement(deckViewer::addOnTop, 1.25);
    }
}
