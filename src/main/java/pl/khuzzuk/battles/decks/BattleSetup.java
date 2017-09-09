package pl.khuzzuk.battles.decks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BattleSetup {
    private List<Card> back;
    private List<Card> left;
    private List<Card> center;
    private List<Card> right;

    public static BattleSetup get(List<Card> back,
                                  List<Card> left,
                                  List<Card> center,
                                  List<Card> right) {
        BattleSetup setup = new BattleSetup();
        setup.back = back;
        setup.left = left;
        setup.center = center;
        setup.right = right;
        return setup;
    }
}
