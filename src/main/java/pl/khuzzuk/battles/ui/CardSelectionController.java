package pl.khuzzuk.battles.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.EventTypes.User;

import static pl.khuzzuk.battles.Battles.BUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardSelectionController {
    @Getter(AccessLevel.PRIVATE)
    CardViewer selected;
    public static CardSelectionController get() {
        CardSelectionController controller = new CardSelectionController();
        BUS.setReaction(User.SELECT_CARD, controller::select);
        return controller;
    }

    private void select(CardViewer cardViewer) {
        selected.deselect();
        selected = cardViewer;
    }
}
