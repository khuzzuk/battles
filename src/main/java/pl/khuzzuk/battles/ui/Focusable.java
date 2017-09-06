package pl.khuzzuk.battles.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Optional;
import java.util.function.Consumer;

public interface Focusable {
    default <T extends Node> void setFocusAnimation(T node, Consumer<T> whenFocus) {
        ScaleTransition focusTransition = new ScaleTransition(Duration.millis(100), node);
        focusTransition.setToX(1.2);
        focusTransition.setToY(1.2);

        ScaleTransition focusLostTransition = new ScaleTransition(Duration.millis(100), node);
        focusLostTransition.setToX(1);
        focusLostTransition.setToY(1);

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

    default void removeFocusAnimation(Node node) {
        node.setOnMouseEntered(null);
        node.setOnMouseExited(null);
    }
}
