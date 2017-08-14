package pl.khuzzuk.battles.ui

import groovy.transform.CompileStatic
import javafx.scene.shape.ClosePath
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@CompileStatic
class CardContentFrame extends Path implements Hexagonal {
    static CardContentFrame get() {
        CardContentFrame frame = new CardContentFrame()
        frame.getElements().addAll(
                new MoveTo(43.4 * 0.907441, 41.2 * 0.907441),
                new LineTo(43.4 * 0.907441, 14.8 * 0.907441),
                new LineTo(70.1 * 0.907441, 0.0 * 0.907441),
                new LineTo(92.0 * 0.907441, 13.5 * 0.907441),
                new LineTo(114.2 * 0.907441, 0.4 * 0.907441),
                new LineTo(135.9 * 0.907441, 13 * 0.907441),
                new LineTo(157.9 * 0.907441, 0.2 * 0.907441),
                new LineTo(184.4 * 0.907441, 14.6 * 0.907441),
                new LineTo(184.4 * 0.907441, 41.1 * 0.907441),
                new LineTo(205.6 * 0.907441, 53.3 * 0.907441),
                new LineTo(205.6 * 0.907441, 79.8 * 0.907441),
                new LineTo(228.5 * 0.907441, 93.0 * 0.907441),
                new LineTo(228.5 * 0.907441, 123.2 * 0.907441),
                new LineTo(205.6 * 0.907441, 136.4 * 0.907441),
                new LineTo(205.6 * 0.907441, 155.6 * 0.907441),
                new LineTo(228.5 * 0.907441, 168.8 * 0.907441),
                new LineTo(228.5 * 0.907441, 198.5 * 0.907441),
                new LineTo(205.6 * 0.907441, 211.8 * 0.907441),
                new LineTo(205.6 * 0.907441, 233.6 * 0.907441),
                new LineTo(228.5 * 0.907441, 246.8 * 0.907441),
                new LineTo(228.5 * 0.907441, 277.7 * 0.907441),
                new LineTo(205.6 * 0.907441, 291.0 * 0.907441),
                new LineTo(205.6 * 0.907441, 317.4 * 0.907441),
                new LineTo(182.8 * 0.907441, 330.6 * 0.907441),
                new LineTo(159.9 * 0.907441, 317.4 * 0.907441),
                new LineTo(137.1 * 0.907441, 330.6 * 0.907441),
                new LineTo(114.2 * 0.907441, 317.4 * 0.907441),
                new LineTo(114.2 * 0.907441, 317.2 * 0.907441),
                new LineTo(91.4 * 0.907441, 330.5 * 0.907441),
                new LineTo(68.5 * 0.907441, 317.2 * 0.907441),
                new LineTo(68.5 * 0.907441, 317.3 * 0.907441),
                new LineTo(45.7 * 0.907441, 330.6 * 0.907441),
                new LineTo(22.8 * 0.907441, 317.3 * 0.907441),
                new LineTo(22.8 * 0.907441, 291.0 * 0.907441),
                new LineTo(0.0 * 0.907441, 277.7 * 0.907441),
                new LineTo(0.0 * 0.907441, 245.8 * 0.907441),
                new LineTo(22.8 * 0.907441, 232.6 * 0.907441),
                new LineTo(22.8 * 0.907441, 211.8 * 0.907441),
                new LineTo(0.0 * 0.907441, 198.5 * 0.907441),
                new LineTo(0.0 * 0.907441, 169.6 * 0.907441),
                new LineTo(22.8 * 0.907441, 156.4 * 0.907441),
                new LineTo(22.8 * 0.907441, 136.9 * 0.907441),
                new LineTo(0.0 * 0.907441, 123.7 * 0.907441),
                new LineTo(0.0 * 0.907441, 93.0 * 0.907441),
                new LineTo(22.8 * 0.907441, 79.8 * 0.907441),
                new LineTo(22.8 * 0.907441, 79.7 * 0.907441),
                new LineTo(22.8 * 0.907441, 53.3 * 0.907441),
                new ClosePath())
        frame
    }
}
