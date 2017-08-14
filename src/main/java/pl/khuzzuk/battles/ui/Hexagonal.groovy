package pl.khuzzuk.battles.ui

import javafx.scene.shape.MoveTo

/**
 * Points are measured ordinal where 0 is the top point, 3 is the bottom and it is counted clockwise
 */
trait Hexagonal {
    MoveTo[] drawLines(int x, int y, int r, int[] points) {
        MoveTo[] drawing = new MoveTo[points.length]
        for (int i = 0; i < points.length; i++) {
            double rad = Math.toRadians(60 * i)
            drawing[i] = new MoveTo(getX(x, r, rad), getY(y, r, rad))
        }
        drawing
    }

    private double getX(double x, double r, double radians) {
        Math.sin(radians) * r + x
    }

    private double getY(double y, double r, double radians) {
        Math.cos(radians) * r + y;
    }
}