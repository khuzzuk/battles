package pl.khuzzuk.battles.ui;

import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardHeader extends Rectangle {
    public static CardHeader get() {
        CardHeader header = new CardHeader();
        header.setWidth(100);
        header.setHeight(100);
        return header;
    }
}
