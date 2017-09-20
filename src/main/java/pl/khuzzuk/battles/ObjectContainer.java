package pl.khuzzuk.battles;

import lombok.experimental.UtilityClass;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.stages.PlayStage;
import pl.khuzzuk.battles.ui.CardSelectionController;

import static pl.khuzzuk.battles.Battles.BUS;

@UtilityClass
class ObjectContainer {
    static void putBeans() {
        putToContainer(Container.GET_CARD_REPO.name(), CardRepository.get());
        putToContainer(Container.GET_PLAY_STAGE.name(), PlayStage.get());
        putToContainer(Container.GET_CARD_SELECTION_CONTROLLER.name(), CardSelectionController.get());
    }

    private static <T> void putToContainer(String eventType, T bean) {
        BUS.setResponse(eventType, () -> bean);
    }
}
