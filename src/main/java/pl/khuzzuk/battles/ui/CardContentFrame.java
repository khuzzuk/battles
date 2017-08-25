package pl.khuzzuk.battles.ui;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class CardContentFrame extends AnchorPane implements Hexagonal, Decorative {
    private final int R;
    private final Image background;
    private double frameScale;
    private int iconsR;
    private Path outer;
    private Path inner;

    private Path strength;
    private Path defence;
    private Path speed;
    private List<Path> equipment;

    static CardContentFrame get(Card card, Image background, int hexSize) {
        CardContentFrame frame = new CardContentFrame(hexSize, background);
        frame.equipment = new ArrayList<>();
        frame.frameScale = frame.R / 8;
        frame.iconsR = (int) (frame.R - frame.frameScale * 2);
        frame.setupShape();
        frame.addCardParams(card);
        return frame;
    }

    private void setupShape() {
        outer = new Path();
        inner = new Path();
        setStartingPoint();

        ObservableList<PathElement> outerElements = outer.getElements();
        ObservableList<PathElement> innerElements = inner.getElements();
        drawWith(0, 2, new int[]{4, 3, 2});
        drawWith(0, 4, new int[]{3, 2});
        drawWith(0, 6, new int[]{3, 2, 1});
        drawWith(1, 7, new int[]{2, 1});
        drawWith(2, 8, new int[]{2, 1, 0});
        drawWith(3, 7, new int[]{1});
        drawWith(4, 8, new int[]{2, 1, 0});
        drawWith(5, 7, new int[]{1});
        drawWith(6, 8, new int[]{2, 1, 0});
        drawWith(7, 7, new int[]{1, 0, 5});
        drawWith(7, 5, new int[]{0, 5});
        drawWith(7, 3, new int[]{0, 5});
        drawWith(7, 1, new int[]{0, 5, 4});
        drawWith(6, 0, new int[]{5, 4, 3});
        drawWith(5, 1, new int[]{4});
        drawWith(4, 0, new int[]{5, 4, 3});
        drawWith(3, 1, new int[]{4});
        drawWith(2, 0, new int[]{5, 4, 3});
        drawWith(1, 1, new int[]{4, 3});
        outerElements.add(new ClosePath());
        innerElements.add(new ClosePath());

        AnchorPane.setTopAnchor(outer, 0d);
        AnchorPane.setTopAnchor(inner, frameScale);
        AnchorPane.setLeftAnchor(outer, 0d);
        AnchorPane.setLeftAnchor(inner, frameScale);
        addDropShadow(outer);
        addInnerShadow(inner);
        setBackground(background, outer);
        setBackground(background, inner);

        getChildren().addAll(outer, inner);
    }

    private void setStartingPoint() {
        getChildren().clear();
        ObservableList<PathElement> outerList = outer.getElements();
        ObservableList<PathElement> innerList = inner.getElements();
        outerList.clear();
        innerList.clear();

        MoveTo startingPoint = getStartingPoint(getCol(2, R), getRow(0, R), R, 5);
        outerList.add(startingPoint);
        innerList.add(new MoveTo(translateX(startingPoint.getX(), 5), translateY(startingPoint.getY(), 5)));
    }

    private void drawWith(int row, int col, int[] points) {
        LineTo[] drawings = drawLines(getCol(col, R), getRow(row, R), R, points);
        LineTo[] innerDrawings = new LineTo[drawings.length];
        int size = drawings.length - 1;
        for (int i = 0; i < size; i++) {
            innerDrawings[i] = new LineTo(
                    translateX(drawings[i].getX(), points[i]),
                    translateY(drawings[i].getY(), points[i]));
        }
        innerDrawings[size] = new LineTo(
                translateXBordered(drawings[size].getX(), points[size]),
                translateYBordered(drawings[size].getY(), points[size]));
        outer.getElements().addAll(drawings);
        inner.getElements().addAll(innerDrawings);
    }

    private void addCardParams(Card card) {
        addIcon(0, 2, "1");
    }

    private void addIcon(int row, int col, String content) {
        int x = getCol(col, R);
        int y = getRow(row, R);
        Path icon = getHex(0, 0, iconsR);
        setBackground(background, icon);
        addDropShadow(icon);

        Path innerIcon = getHex(0, 0, (int) (iconsR - frameScale));
        setBackground(background, innerIcon);
        addInnerShadow(innerIcon);

        AnchorPane.setLeftAnchor(icon, x + frameScale * 2);
        AnchorPane.setTopAnchor(icon, (double) y - frameScale);
        AnchorPane.setLeftAnchor(innerIcon, x + frameScale * 3 - 0.5);
        AnchorPane.setTopAnchor(innerIcon, (double) y);
        getChildren().addAll(icon, innerIcon);
    }

    private double translateX(double x, int position) {
        switch (position) {
            case 0:
                return x;
            case 1:
            case 2:
                return x - frameScale;
            case 3:
                return x;
            case 4:
            case 5:
                return x + frameScale;
            default:
                return x;
        }
    }

    private double translateY(double y, int position) {
        switch (position) {
            case 0:
                return y - frameScale;
            case 1:
                return y - frameScale / 2;
            case 2:
                return y + frameScale / 2;
            case 3:
                return y + frameScale;
            case 4:
                return y + frameScale / 2;
            case 5:
                return y - frameScale / 2;
            default:
                return y;
        }
    }

    private double translateXBordered(double x, int position) {
        switch (position) {
            case 0:
            case 1:
                return x - frameScale;
            case 2:
                return x;
            case 3:
            case 4:
                return x + frameScale;
            default:
                return x;
        }
    }

    private double translateYBordered(double y, int position) {
        switch (position) {
            case 0:
                return y - frameScale / 2;
            case 1:
                return y + frameScale / 2;
            case 2:
                return y + frameScale;
            case 3:
                return y + frameScale / 2;
            case 4:
                return y - frameScale / 2;
            case 5:
                return y - frameScale;
            default:
                return y;
        }
    }

    private int getRow(int num, int r) {
        return (int) (10 + (r * 1.5) * num);
    }

    private int getCol(int num, int r) {
        return num * (r - (r / 10 + 1));
    }
}
