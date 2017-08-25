package pl.khuzzuk.battles.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Speed {
    SLOW(1), MEDIUM(2), FAST(3);
    public final int value;
}
