package pl.khuzzuk.battles.functions;

import lombok.experimental.UtilityClass;

import static java.lang.Math.max;
import static java.lang.Math.min;

@UtilityClass
public class Calculations {
    public static int middle(int lowBound, int element, int highBound) {
        return max(lowBound, min(element, highBound));
    }
}
