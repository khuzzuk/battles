package pl.khuzzuk.battles.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import pl.khuzzuk.functions.Switcher;
import pl.khuzzuk.messaging.Bus;

import java.util.function.Consumer;

public interface Selectable<T extends Pane, U extends Shape> extends Element<T> {
    default void addSelectionEffect(Consumer<? super Selectable<T, U>> whenSelected, Bus bus, Enum<?> event) {
        T node = getElement();
        U background = getBackElement();
        Paint baseStroke = background.getStroke();
        background.setStrokeWidth(3);

        Switcher switcher = Switcher.get(
                () -> {
                    background.setStroke(Color.WHITE);
                    whenSelected.accept(this);
                },
                () -> background.setStroke(baseStroke));
        node.setOnMouseClicked(mouseEvent -> bus.send(event.name(), switcher));
    }

    U getBackElement();
}
