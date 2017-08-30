package pl.khuzzuk.battles.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Focusable {
    default void setFocusAnimation(Node node) {
        AtomicBoolean animating = new AtomicBoolean(false);
        ScaleTransition focusTransition = new ScaleTransition(Duration.millis(100), node);
        focusTransition.setByX(.2);
        focusTransition.setByY(.2);
        focusTransition.setOnFinished(e -> animating.set(false));

        ScaleTransition focusLostTransition = new ScaleTransition(Duration.millis(100), node);
        focusLostTransition.setByX(-.2);
        focusLostTransition.setByY(-.2);
        focusLostTransition.setOnFinished(e -> animating.set(false));

        AtomicBoolean enlarged = new AtomicBoolean(false);
        node.setOnMouseEntered(Optional.ofNullable(node.getOnMouseEntered())
                .orElseGet(() -> event -> {
            if (!enlarged.get() && !animating.get()) {
                animating.set(true);
                focusTransition.play();
                enlarged.set(true);
            }
        }));
        node.setOnMouseExited(Optional.ofNullable(node.getOnMouseExited())
                .orElseGet(() -> event -> {
            if (enlarged.get() && !animating.get()) {
                animating.set(true);
                focusLostTransition.play();
                enlarged.set(false);
            }
        }));
    }

    default void removeFocusAnimation(Node node) {
        node.setOnMouseEntered(null);
        node.setOnMouseExited(null);
    }
}
