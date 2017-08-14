package pl.khuzzuk.battles.ui

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class CardHeader extends Rectangle {
    static CardHeader get() {
        CardHeader header = new CardHeader()
        header.setFill(Color.BLACK)
        header.setWidth(100)
        header.setHeight(100)
        header
    }
}
