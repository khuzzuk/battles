package pl.khuzzuk.battles.ui.decorators;

import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;
import pl.khuzzuk.messaging.Bus;

import java.util.ArrayList;
import java.util.List;

public interface CardDecorator {
    void addBehavior(DeckViewer deckViewer, CardViewer cardViewer);
}
