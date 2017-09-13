package pl.khuzzuk.battles.ui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DamageViewer extends AnchorPane {
    private Rectangle backElement;
    static DamageViewer get(double startingWidth, double startingHeight) {
        DamageViewer damageViewer = new DamageViewer();
        damageViewer.setWidth(startingWidth);
        damageViewer.setHeight(startingHeight);
        damageViewer.setupBack();
        return damageViewer;
    }

    private void setupBack() {
        backElement = new Rectangle(getWidth(), getHeight());
        backElement.setStrokeWidth(0);
        backElement.setFill(new Color(0.01, 0.01, 0.01, 0.5));
        AnchorPane.setLeftAnchor(backElement, 0d);
        AnchorPane.setTopAnchor(backElement, 0d);
        getChildren().add(backElement);
    }

    private void addPlayerDeck(DeckViewer viewer) {
        AnchorPane.setLeftAnchor(viewer, 0d);
        AnchorPane.setTopAnchor(viewer, viewer.getHeight());
        getChildren().add(viewer);
    }

    private void addOpponentDeck(DeckViewer viewer) {
        AnchorPane.setLeftAnchor(viewer, 0d);
        AnchorPane.setTopAnchor(viewer, 0d);
        getChildren().add(viewer);
    }
}
