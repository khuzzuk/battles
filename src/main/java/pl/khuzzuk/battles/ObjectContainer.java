package pl.khuzzuk.battles;

import lombok.experimental.UtilityClass;
import pl.khuzzuk.battles.EventTypes.Container;
import pl.khuzzuk.battles.cards.CardRepository;
import pl.khuzzuk.battles.stages.DamageResolver;
import pl.khuzzuk.battles.stages.PlayStage;
import pl.khuzzuk.battles.ui.CardSelectionController;
import pl.khuzzuk.messaging.Bus;

@UtilityClass
class ObjectContainer {
    static void putBeans(Bus bus) {
        putToContainer(bus, Container.GET_CARD_REPO, CardRepository.get());
        putToContainer(bus, Container.GET_PLAY_STAGE, PlayStage.get(bus));
        putToContainer(bus, Container.GET_CARD_SELECTION_CONTROLLER, CardSelectionController.get(bus, EventTypes.User.SELECT_CARD));
        putToContainer(bus, Container.GET_OPPONENT_CARD_SELECTION_CONTROLLER, CardSelectionController.get(bus, EventTypes.User.SELECT_OPPONENT_CARD));
        putToContainer(bus, Container.GET_DAMAGE_RESOLVER, DamageResolver.get(bus));
    }

    static <T> void putToContainer(Bus bus, Enum<?> eventType, T bean) {
        bus.setResponse(eventType.name(), () -> bean);
    }
}
