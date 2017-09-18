package pl.khuzzuk.battles.ui;

import pl.khuzzuk.battles.cards.Card;

class SelectableDeckViewer extends DeckViewer {
    private SelectableDeckViewer() {
        super();
    }

    static SelectableDeckViewer get(double width, double height) {
        SelectableDeckViewer deckViewer = new SelectableDeckViewer();
        deckViewer.setInitialValues(width, height);
        return deckViewer;
    }

    @Override
    CardViewer addCard(Card card) {
        CardViewer viewer = CardViewer.instance(card, (int) getMaxHeight());
        getDeck().add(viewer);
        viewer.addSelectionEffect(this::markSelected);
        return viewer;
    }

    private void markSelected(Selectable<CardViewer, ?> cardViewer) {
        addOnTop(cardViewer.getElement());
    }

    private void deselect(Selectable<CardViewer, ?> cardViewer) {

    }
}
