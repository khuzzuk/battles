package pl.khuzzuk.battles;

import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.cards.CardRepository;

import static pl.khuzzuk.battles.Battles.BUS;

public class ObjectContainer {
    static void putBeans() {
        putToContainer(Container.GET_CARD_REPO, CardRepository.get());
    }

    private static <T> void putToContainer(String eventType, T bean) {
        BUS.setResponse(eventType, () -> bean);
    }
}
