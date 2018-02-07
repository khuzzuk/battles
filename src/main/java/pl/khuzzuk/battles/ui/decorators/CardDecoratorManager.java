package pl.khuzzuk.battles.ui.decorators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.khuzzuk.battles.cards.Card;
import pl.khuzzuk.battles.ui.CardViewer;
import pl.khuzzuk.battles.ui.DeckViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDecoratorManager {
    private Map<Predicate<Card>, CardDecorator> filtered;
    private List<CardDecorator> unfiltered;

    public static CardDecoratorManager get() {
        CardDecoratorManager manager = new CardDecoratorManager();
        manager.filtered = new HashMap<>();
        manager.unfiltered = new ArrayList<>();
        return manager;
    }

    public CardDecoratorManager addFiltered(@NonNull Predicate<Card> filter, @NonNull CardDecorator decorator) {
        filtered.put(filter, decorator);
        return this;
    }

    public CardDecoratorManager addUnfiltered(@NonNull CardDecorator decorator) {
        unfiltered.add(decorator);
        return this;
    }

    public void handle(CardViewer cardViewer, DeckViewer deckViewer) {
        filtered.entrySet().stream()
                .filter(entry -> entry.getKey().test(cardViewer.getCard()))
                .forEach(entry -> entry.getValue().addBehavior(deckViewer, cardViewer));
        unfiltered.forEach(decorator -> decorator.addBehavior(deckViewer, cardViewer));
    }
}
