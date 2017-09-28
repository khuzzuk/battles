package pl.khuzzuk.battles.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class CardContentFrame extends PositionablePane implements Hexagonal, Decorative {
    private final int hexR;
    private final Image background;
    private double frameScale;
    private int iconsR;
    private Path outer;
    private Path inner;

    static CardContentFrame get(Card card, Image background, int hexSize) {
        CardContentFrame frame = new CardContentFrame(hexSize, background);
        frame.frameScale = frame.hexR / 8d;
        frame.iconsR = (int) (frame.hexR - frame.frameScale * 1.5);
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

        MoveTo startingPoint = getStartingPoint(getCol(2, hexR), getRow(0, hexR), hexR, 5);
        outerList.add(startingPoint);
        innerList.add(new MoveTo(translateX(startingPoint.getX(), 5), translateY(startingPoint.getY(), 5)));
    }

    private void drawWith(int row, int col, int[] points) {
        LineTo[] drawings = drawLines(getCol(col, hexR), getRow(row, hexR), hexR, points);
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
        addIcon(0, 2, String.valueOf(card.getStrength()));
        addIcon(0, 4, String.valueOf(card.getSpeed().value));
        addIcon(0, 6, String.valueOf(card.getDefence()));
        addIcon(1, 7, String.valueOf(card.getReach().getDistance()));
        addIcon(7, 7, String.valueOf(card.getCost()));
    }

    private void addIcon(int row, int col, String content) {
        int x = getCol(col, hexR);
        double topAnchor = (hexR * 3d / 2 + 0.25) * row + frameScale * 2;
        double leftAnchor = x + frameScale * 2 - 1;

        Path icon = getHex(0, 0, iconsR);
        setBackground(background, icon);
        addDropShadow(icon);
        positionElement(icon, leftAnchor, topAnchor);

        Path innerIcon = getHex(0, 0, (int) (iconsR - frameScale / 2));
        setBackground(background, innerIcon);
        addInnerShadow(innerIcon);
        positionElement(innerIcon, leftAnchor + frameScale, topAnchor + frameScale);

        HBox textBox = new HBox();
        Label text = new Label(content);
        text.setFont(Font.font(iconsR));
        textBox.getChildren().addAll(text);
        textBox.setAlignment(Pos.CENTER);
        double boxSize = iconsR * 2d;
        textBox.setMinHeight(boxSize);
        textBox.setMinWidth(boxSize);
        positionElement(textBox, leftAnchor - 1, topAnchor);
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
