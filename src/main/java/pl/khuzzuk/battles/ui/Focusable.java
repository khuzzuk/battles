package pl.khuzzuk.battles.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Optional;
import java.util.function.Consumer;

public interface Focusable<T extends Node> extends Element<T> {
    default void setFocusToMouseMovement(Consumer<T> whenFocus, double scale) {
        T node = getElement();
        ScaleTransition focusTransition = getFocusTransition(scale);
        ScaleTransition focusLostTransition = getFocusTransition(1);
        node.setOnMouseEntered(Optional.ofNullable(node.getOnMouseEntered())
                .orElseGet(() -> event -> {
                    whenFocus.accept(node);
                    focusTransition.play();
                }));
        node.setOnMouseExited(Optional.ofNullable(node.getOnMouseExited())
                .orElseGet(() -> event -> {
                    focusLostTransition.play();
                }));
    }

    //TODO make private with java9
    default ScaleTransition getFocusTransition(double scale) {
        T node = getElement();
        ScaleTransition focusTransition = new ScaleTransition(Duration.millis(100), node);
        focusTransition.setToX(scale);
        focusTransition.setToY(scale);
        return focusTransition;
    }
}
