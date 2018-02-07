package pl.khuzzuk.battles.ui;

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Points are measured ordinal where 3 is the top point, 0 is the bottom and it is counted counter clockwise: <br/>
 * __3 <br/>
 * 4___2 <br/>
 * 5___1 <br/>
 * __0 <br/>
 */
public interface Hexagonal {
    default LineTo[] drawLines(int x, int y, double r, int[] points) {
        LineTo[] drawing = new LineTo[points.length];
        for (int i = 0; i < points.length; i++) {
            double rad = Math.toRadians((double) 60 * points[i]);
            drawing[i] = new LineTo(getX(x, r, rad), getY(y, r, rad));
        }

        return drawing;
    }

    default MoveTo getStartingPoint(int x, int y, double r, int point) {
        double rad = Math.toRadians((double) 60 * point);
        return new MoveTo(getX(x, r, rad), getY(y, r, rad));
    }

    default double getX(double x, double r, double radians) {
        return Math.sin(radians) * r + x;
    }

    default double getY(double y, double r, double radians) {
        return Math.cos(radians) * r + y;
    }

    default Path getHex(int x, int y, double r) {
        Path icon = new Path(getStartingPoint(x, y, r, 0));
        icon.getElements().addAll(drawLines(x, y, r, new int[]{1, 2, 3, 4, 5}));
        icon.getElements().addAll(new ClosePath());
        return icon;
    }
}
