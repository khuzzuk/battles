package pl.khuzzuk.battles.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.function.Consumer;

public interface Selectable<T extends Pane> {
    default void addSelectionEffect(Shape background,
                                    Consumer<? super Selectable<T>> whenSelected,
                                    Consumer<? super Selectable<T>> whenUnselected) {
        Pane node = getElement();
        Paint baseStroke = background.getStroke();
        background.setStrokeWidth(2);
        node.setOnMouseClicked(event -> {
            if (isSelected()) {
                setSelected(false);
                background.setStroke(baseStroke);
                whenSelected.accept(this);
            } else {
                setSelected(true);
                background.setStroke(Color.WHITE);
                whenUnselected.accept(this);
            }
        });
    }

    T getElement();

    boolean isSelected();

    void setSelected(boolean isSelected);
}
