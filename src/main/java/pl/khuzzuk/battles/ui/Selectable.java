package pl.khuzzuk.battles.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import pl.khuzzuk.battles.EventTypes;
import pl.khuzzuk.battles.functions.Switcher;

import java.util.function.Consumer;

import static pl.khuzzuk.battles.Battles.BUS;

public interface Selectable<T extends Pane, U extends Shape> extends Element<T> {
    default void addSelectionEffect(Consumer<? super Selectable<T, U>> whenSelected) {
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
        node.setOnMouseClicked(event -> BUS.send(EventTypes.User.SELECT_CARD.name(), switcher));
    }

    U getBackElement();
}
