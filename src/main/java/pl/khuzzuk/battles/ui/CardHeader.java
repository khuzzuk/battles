package pl.khuzzuk.battles.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardHeader extends AnchorPane implements Decorative {
    public static CardHeader get(Image background, int width, String name) {
        int height = width / 4;
        CardHeader header = new CardHeader();

        Rectangle outer = new Rectangle(width, height);
        header.setBackground(background, outer);
        header.addDropShadow(outer);

        Rectangle inner = new Rectangle(width - 10, height - 10);
        header.setBackground(background, inner);
        header.addInner(inner, width / 2, height / 2, width / 2);
        AnchorPane.setTopAnchor(inner, 5d);
        AnchorPane.setLeftAnchor(inner, 5d);

        Label text = new Label(name);
        text.setFont(Font.font(width / 10));
        HBox textContainer = new HBox(text);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setPrefWidth(width - 10);
        textContainer.setPrefHeight(height - 10);
        AnchorPane.setTopAnchor(textContainer, 5d);
        AnchorPane.setLeftAnchor(textContainer, 5d);

        header.setWidth(width);
        header.setHeight(width / 4);
        header.getChildren().addAll(outer, inner, textContainer);
        return header;
    }
}
