package pl.khuzzuk.battles.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class DamageViewer extends PositionablePane {
    static DamageViewer get(double startingWidth, double startingHeight) {
        DamageViewer damageViewer = new DamageViewer();
        damageViewer.setWidth(startingWidth);
        damageViewer.setHeight(startingHeight);
        damageViewer.setupBack();
        return damageViewer;
    }

    private void setupBack() {
        Rectangle backElement = new Rectangle(getWidth(), getHeight());
        backElement.setStrokeWidth(0);
        backElement.setFill(new Color(0.01, 0.01, 0.01, 0.5));
        positionElement(backElement, 0d, 0d);
    }

    void addPlayerDeck(DeckViewer viewer) {
        positionElement(viewer, 0d, viewer.getHeight());
    }

    void addOpponentDeck(DeckViewer viewer) {
        positionElement(viewer, 0d, 0d);
    }
}
