package pl.khuzzuk.battles.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.battles.cards.Card;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class CardViewer extends AnchorPane implements Decorative, Focusable<CardViewer>, Selectable<CardViewer, Rectangle> {
    private static final Map<String, Image> cacheOfImages = new HashMap<>();
    private final Card card;
    @Setter
    private boolean selected;
    private Rectangle backElement;
    private Color baseStroke;

    static CardViewer instance(Card card, int areaHeight) {
        CardViewer viewer = new CardViewer(card);
        viewer.prepareFrame(cacheOfImages.computeIfAbsent(card.getStyle().getBackgroundPath(), Image::new), areaHeight / 18);
        return viewer;
    }

    private void prepareFrame(Image backgroundImage, int size) {
        backElement = new Rectangle(size * 10, size * 16);
        backElement.setArcWidth(size);
        backElement.setArcHeight(size);
        setBackground(backgroundImage, backElement);
        backElement.setStrokeWidth(1d);
        baseStroke = Color.BLACK;
        backElement.setStroke(baseStroke);

        CardContentFrame frame = CardContentFrame.get(card, backgroundImage, size);
        AnchorPane.setTopAnchor(frame, size * 3d);
        AnchorPane.setBottomAnchor(frame, size / 2d);
        AnchorPane.setLeftAnchor(frame, size / 2d);
        AnchorPane.setRightAnchor(frame, size / 2d);

        CardHeader header = CardHeader.get(backgroundImage, size * 8, card.getName());
        AnchorPane.setTopAnchor(header, size / 3d);
        AnchorPane.setBottomAnchor(header, frame.getHeight() + 10d);
        AnchorPane.setLeftAnchor(header, 10 + size / 2d);
        AnchorPane.setRightAnchor(header, 10 + size / 2d);

        getChildren().addAll(backElement, frame, header);
    }

    @Override
    public CardViewer getElement() {
        return this;
    }
}
