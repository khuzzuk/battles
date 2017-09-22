package pl.khuzzuk.battles.ui;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.messaging.Bus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class SelectableDeckViewer extends DeckViewer {
    @NonNull
    private Bus bus;
    private Enum<?> event;

    static SelectableDeckViewer get(double width, double height, Bus bus, Enum<?> event) {
        SelectableDeckViewer deckViewer = new SelectableDeckViewer(bus);
        deckViewer.setInitialValues(width, height);
        deckViewer.event = event;
        return deckViewer;
    }

    @Override
    CardViewer addCard(Card card) {
        CardViewer viewer = CardViewer.instance(card, (int) getMaxHeight());
        getDeck().add(viewer);
        viewer.addSelectionEffect(this::markSelected, bus, event);
        return viewer;
    }

    private void markSelected(Selectable<CardViewer, ?> cardViewer) {
        addOnTop(cardViewer.getElement());
    }
}
