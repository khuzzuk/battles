package pl.khuzzuk.battles.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class PositionablePane extends AnchorPane {
    protected void positionElement(Node node, double left, double top) {
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setTopAnchor(node, top);
        getChildren().add(node);
    }
}
