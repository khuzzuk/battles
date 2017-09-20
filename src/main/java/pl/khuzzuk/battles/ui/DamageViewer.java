package pl.khuzzuk.battles.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class DamageViewer extends PositionablePane {
    static DamageViewer get(double startingWidth, double startingHeight,
                            DeckViewer playerDeck, DeckViewer opponentDeck) {
        DamageViewer damageViewer = new DamageViewer();
        damageViewer.setWidth(startingWidth);
        damageViewer.setHeight(startingHeight);
        damageViewer.setupBack();
        damageViewer.positionElement(opponentDeck, 0d, 0d);
        damageViewer.positionElement(playerDeck, 0d, startingHeight / 2);
        return damageViewer;
    }

    private void setupBack() {
        Rectangle backElement = new Rectangle(getWidth(), getHeight());
        backElement.setStrokeWidth(0);
        backElement.setFill(new Color(0.01, 0.01, 0.01, 0.5));
        positionElement(backElement, 0d, 0d);
    }
}
