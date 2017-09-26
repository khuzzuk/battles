package pl.khuzzuk.battles.ui.decorators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;
import pl.khuzzuk.messaging.Bus;

@RequiredArgsConstructor
public class SelectableDecorator implements CardDecorator {
    @NonNull
    private Enum<?> eventType;
    @NonNull
    private Bus bus;


    @Override
    public void addBehavior(DeckViewer deckViewer, CardViewer cardViewer) {
        cardViewer.addSelectionEffect(() -> deckViewer.addOnTop(cardViewer), bus, eventType);
    }
}
