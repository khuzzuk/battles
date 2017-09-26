package pl.khuzzuk.battles.ui.decorators;

import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;
import pl.khuzzuk.messaging.Bus;

import java.util.ArrayList;
import java.util.List;

public interface CardDecorator {
    void addBehavior(DeckViewer deckViewer, CardViewer cardViewer);

    static List<CardDecorator> getSelectableDecorators(Bus bus, Enum<?> eventType) {
        List<CardDecorator> cardDecorators = new ArrayList<>();
        cardDecorators.add(new FocusableDecorator());
        cardDecorators.add(new SelectableDecorator(eventType, bus));
        return cardDecorators;
    }
}
