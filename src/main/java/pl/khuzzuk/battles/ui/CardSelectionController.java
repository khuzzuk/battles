package pl.khuzzuk.battles.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.User;
import pl.khuzzuk.battles.functions.Switcher;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardSelectionController {
    private static Runnable emptyDeselection = () -> {};
    private static Switcher emptySwitcher = Switcher.get(emptyDeselection , emptyDeselection);
    private Switcher switcher;
    public static CardSelectionController get() {
        CardSelectionController controller = new CardSelectionController();
        controller.switcher = emptySwitcher;
        BUS.setGuiReaction(User.SELECT_CARD, controller::select);
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
