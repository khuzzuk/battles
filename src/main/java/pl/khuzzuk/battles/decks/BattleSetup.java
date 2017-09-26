package pl.khuzzuk.battles.decks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BattleSetup {
    private Deck back;
    private Deck left;
    private Deck center;
    private Deck right;

    public static BattleSetup get(Deck back,
                                  Deck left,
                                  Deck center,
                                  Deck right) {
        BattleSetup setup = new BattleSetup();
        setup.back = back;
        setup.left = left;
        setup.center = center;
        setup.right = right;
        return setup;
    }
}
