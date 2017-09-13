package pl.khuzzuk.battles.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.function.Consumer;

public interface Selectable<T extends Pane, U extends Shape> extends Element<T> {
    default void addSelectionEffect(Consumer<? super Selectable<T, U>> whenSelected,
                                    Consumer<? super Selectable<T, U>> whenUnselected) {
        T node = getElement();
        U background = getBackElement();
        Paint baseStroke = background.getStroke();
        background.setStrokeWidth(2);
        node.setOnMouseClicked(event -> {
            if (isSelected()) {
                deselect();
                whenSelected.accept(this);
            } else {
                select();
                whenUnselected.accept(this);
            }
        });
    }

    U getBackElement();

    Paint getBaseStroke();

    boolean isSelected();

    void setSelected(boolean isSelected);

    default void deselect() {
        getBackElement().setStroke(getBaseStroke());
        setSelected(false);
    }

    default void select() {
        getBackElement().setStroke(Color.WHITE);
        setSelected(true);
    }
}
