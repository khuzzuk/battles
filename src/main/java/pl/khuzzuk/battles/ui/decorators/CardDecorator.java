package pl.khuzzuk.battles.ui.decorators;

import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;

public interface CardDecorator {
    void addBehavior(DeckViewer deckViewer, CardViewer cardViewer);
}
