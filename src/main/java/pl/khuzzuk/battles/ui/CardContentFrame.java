package pl.khuzzuk.battles.ui;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class CardContentFrame extends AnchorPane implements Hexagonal, Decorative {
    private final int R;
    private final int r;
    private double frameScale = 6;
    private Path outer;
    private Path inner;

    static CardContentFrame get(Image background, int hexSize) {
        CardContentFrame frame = new CardContentFrame(hexSize, (int) (hexSize * 0.7));
        frame.setupShape(background);
        return frame;
    }

    private void setupShape(Image background) {
        outer = new Path();
        inner = new Path();
        setStartingPoint();

        ObservableList<PathElement> outerElements = outer.getElements();
        ObservableList<PathElement> innerElements = inner.getElements();
        drawWith(0, 1, new int[]{4, 3, 2});
        drawWith(0, 3, new int[]{3, 2, 1, 0, 5});
        drawWith(0, 1, new int[]{0, 5});
        //drawWith(0, 1, new int[]{4, 3, 2});
        //drawWith(0, 3, new int[]{3, 2});
        //drawWith(0, 5, new int[]{3, 2, 1});
        //drawWith(1, 6, new int[]{2, 1});
        //drawWith(2, 7, new int[]{2, 1, 0});
        //drawWith(3, 6, new int[]{1});
        //drawWith(4, 7, new int[]{2, 1, 0});
        //drawWith(5, 6, new int[]{1});
        //drawWith(6, 7, new int[]{2, 1, 0});
        //drawWith(7, 6, new int[]{1, 0, 5});
        //drawWith(7, 4, new int[]{0, 5});
        //drawWith(7, 2, new int[]{0, 5});
        //drawWith(7, 0, new int[]{0, 5, 4});
        //drawWith(6, -1, new int[]{5, 4, 3});
        //drawWith(5, 0, new int[]{4});
        //drawWith(4, -1, new int[]{5, 4, 3});
        //drawWith(3, 0, new int[]{4});
        //drawWith(2, -1, new int[]{5, 4, 3});
        //drawWith(1, 0, new int[]{4});
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
        ObservableList<PathElement> outerList = outer.getElements();
        ObservableList<PathElement> innerList = inner.getElements();
        outerList.clear();
        innerList.clear();

        MoveTo startingPoint = getStartingPoint(getCol(1, R), getRow(0, R), R, 5);
        outerList.add(startingPoint);
        innerList.add(new MoveTo(translateX(startingPoint.getX(), 5), translateY(startingPoint.getY(), 5)));
    }

    private void drawWith(int row, int col, int[] points) {
        LineTo[] drawings = drawLines(getCol(col, R), getRow(row, R), R, points);
        LineTo[] innerDrawings = new LineTo[drawings.length];
        for (int i = 0; i < drawings.length; i++) {
            innerDrawings[i] = new LineTo(
                    translateX(drawings[i].getX(), points[i]),
                    translateY(drawings[i].getY(), points[i]));
        }
        outer.getElements().addAll(drawings);
        inner.getElements().addAll(innerDrawings);
    }

    private double translateX(double x, int position) {
        switch (position) {
            case 0:
                return x;
            case 1:
                return x - frameScale;
            case 2:
                return x - frameScale;
            case 3:
                return x;
            case 4:
                return x + frameScale;
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
                return y - frameScale;
            case 2:
                return y + frameScale;
            case 3:
                return y + frameScale;
            case 4:
                return y + frameScale;
            case 5:
                return y - frameScale;
            default:
                    return y;
        }
    }

    private double translateXBordered(double x, int position) {
        switch (position) {
            case 0:
                return x;
            case 1:
                return x - frameScale;
            case 2:
                return x - frameScale;
            case 3:
                return x;
            case 4:
                return x + frameScale;
            case 5:
                return x + frameScale;
            default:
                return x;
        }
    }

    private double translateYBordered(double y, int position) {
        switch (position) {
            case 0:
                return y - frameScale;
            case 1:
                return y - frameScale;
            case 2:
                return y + frameScale;
            case 3:
                return y + frameScale;
            case 4:
                return y + frameScale;
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
