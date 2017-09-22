package pl.khuzzuk.battles.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.functions.Switcher;
import pl.khuzzuk.messaging.Bus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardSelectionController {
    private static Runnable emptyDeselection = () -> {};
    private static Switcher emptySwitcher = Switcher.get(emptyDeselection , emptyDeselection);
    private Switcher switcher;
    public static CardSelectionController get(Bus bus, Enum<?> event) {
        CardSelectionController controller = new CardSelectionController();
        controller.switcher = emptySwitcher;
        bus.setGuiReaction(event.name(), controller::select);
        return controller;
    }

    private void select(Switcher switcher) {
        if (switcher != this.switcher) {
            this.switcher.off();
        }
        switcher.change();
        this.switcher = switcher;
    }
}
