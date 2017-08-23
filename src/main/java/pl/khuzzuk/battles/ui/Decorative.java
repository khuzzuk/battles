package pl.khuzzuk.battles.ui;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public interface Decorative {
    default void setBackground(Image background, Shape element) {
        element.setFill(new ImagePattern(background, 0, 0, 1, 1, true));
        element.setStrokeWidth(0);
    }

    default void addDropShadow(Shape element) {
        element.setEffect(new DropShadow(10, 0, 0, Color.BLACK));
    }

    default void addInner(Shape element, int x, int y, int distance) {
        InnerShadow shadow = new InnerShadow(10, 0, 0, Color.BLACK);
        Light.Point light = new Light.Point(x, y, distance, Color.WHITE);
        Lighting lighting = new Lighting(light);
        shadow.setInput(lighting);
        element.setEffect(shadow);
    }

    default void addInnerShadow(Shape element) {
        element.setEffect(new InnerShadow(10, 0, 0, Color.BLACK));
    }

    default void clearEffects(Shape element) {
        element.setEffect(null);
    }
}
