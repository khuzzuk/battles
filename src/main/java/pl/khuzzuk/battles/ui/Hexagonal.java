package pl.khuzzuk.battles.ui;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 * Points are measured ordinal where 3 is the top point, 0 is the bottom and it is counted counter clockwise: <br/>
 * __3 <br/>
 * 2___4 <br/>
 * 1___5 <br/>
 * __0 <br/>
 */
public interface Hexagonal {
    default LineTo[] drawLines(int x, int y, int r, Integer[] points) {
        LineTo[] drawing = new LineTo[points.length];
        for (int i = 0; i < points.length; i++) {
            double rad = Math.toRadians((double) 60 * points[i]);
            drawing[i] = new LineTo(getX(x, r, rad), getY(y, r, rad));
        }

        return drawing;
    }

    default MoveTo getStartingPoint(int x, int y, int r, int point) {
        double rad = Math.toRadians((double) 60 * point);
        return new MoveTo(getX(x, r, rad), getY(y, r, rad));
    }

    default double getX(double x, double r, double radians) {
        return Math.sin(radians) * r + x;
    }

    default double getY(double y, double r, double radians) {
        return Math.cos(radians) * r + y;
    }
}
