package pl.khuzzuk.battles.ui;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CardContentFrame extends Path implements Hexagonal {
    private final int r;

    public static CardContentFrame get(Image background, int hexSize) {
        CardContentFrame frame = new CardContentFrame(hexSize);
        frame.setupShape();
        frame.setBackground(background);
        return frame;
    }

    private void setupShape() {
        ObservableList<PathElement> elements = getElements();
        elements.addAll(getStartingPoint(getCol(1), getRow(0), r, 5));
        elements.addAll(drawLines(getCol(1), getRow(0), r, new Integer[]{4, 3, 2}));
        elements.addAll(drawLines(getCol(3), getRow(0), r, new Integer[]{3, 2}));
        elements.addAll(drawLines(getCol(5), getRow(0), r, new Integer[]{3, 2, 1}));
        elements.addAll(drawLines(getCol(6), getRow(1), r, new Integer[]{2, 1}));
        elements.addAll(drawLines(getCol(7), getRow(2), r, new Integer[]{2, 1, 0}));
        elements.addAll(drawLines(getCol(6), getRow(3), r, new Integer[]{1}));
        elements.addAll(drawLines(getCol(7), getRow(4), r, new Integer[]{2, 1, 0}));
        elements.addAll(drawLines(getCol(6), getRow(5), r, new Integer[]{1}));
        elements.addAll(drawLines(getCol(7), getRow(6), r, new Integer[]{2, 1, 0}));
        elements.addAll(drawLines(getCol(6), getRow(7), r, new Integer[]{1, 0, 5}));
        elements.addAll(drawLines(getCol(4), getRow(7), r, new Integer[]{0, 5}));
        elements.addAll(drawLines(getCol(2), getRow(7), r, new Integer[]{0, 5}));
        elements.addAll(drawLines(getCol(0), getRow(7), r, new Integer[]{0, 5, 4}));
        elements.addAll(drawLines(getCol(-1), getRow(6), r, new Integer[]{5, 4, 3}));
        elements.addAll(drawLines(getCol(0), getRow(5), r, new Integer[]{4}));
        elements.addAll(drawLines(getCol(-1), getRow(4), r, new Integer[]{5, 4, 3}));
        elements.addAll(drawLines(getCol(0), getRow(3), r, new Integer[]{4}));
        elements.addAll(drawLines(getCol(-1), getRow(2), r, new Integer[]{5, 4, 3}));
        elements.addAll(drawLines(getCol(0), getRow(1), r, new Integer[]{4}));
        elements.add(new ClosePath());
    }

    private void setBackground(Image background) {
        setFill(new ImagePattern(background, 0, 0, 1, 1, true));
    }

    private int getRow(int num) {
        return (int) (10 + (r * 1.5) * num);
    }

    private int getCol(int num) {
        return num * (r - (r / 10 + 1));
    }
}
