package pl.khuzzuk.battles.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Reach {
    SHORT(1), SPEAR(2), LONG_SPEAR(3), SHORT_RANGE_MISSILE(4), MISSILE(5), LONG_RANGE_MISSILE(6);

    @Getter
    private final int distance;
}
