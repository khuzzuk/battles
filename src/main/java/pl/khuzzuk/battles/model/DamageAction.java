package pl.khuzzuk.battles.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import pl.khuzzuk.battles.cards.Card;

@Builder
@Getter
public class DamageAction {
    @NonNull
    private Card attacker;
    @NonNull
    private Card defender;
    @NonNull
    private Side attackingSide;
    @NonNull
    private Side defendingSide;
}
