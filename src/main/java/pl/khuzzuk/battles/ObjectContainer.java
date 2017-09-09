package pl.khuzzuk.battles;

import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.stages.PlayStage;

import static pl.khuzzuk.battles.Battles.BUS;

class ObjectContainer {
    static void putBeans() {
        putToContainer(Container.GET_CARD_REPO, CardRepository.get());
        putToContainer(Container.GET_PLAY_STAGE, PlayStage.get());
    }

    private static <T> void putToContainer(String eventType, T bean) {
        BUS.setResponse(eventType, () -> bean);
    }
}
