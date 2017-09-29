package pl.khuzzuk.battles.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class DamageOrder implements Comparable<DamageOrder> {
    private Reach reach;
    private Speed speed;

    @Override
    public int compareTo(DamageOrder other) {
        return reach.getDistance() * 100 - other.reach.getDistance() * 100 + speed.value - other.speed.value;
    }
}
