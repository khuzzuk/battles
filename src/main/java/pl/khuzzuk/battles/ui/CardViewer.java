package pl.khuzzuk.battles.ui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class CardViewer extends PositionablePane implements Decorative, Focusable<CardViewer>, Selectable<CardViewer, Rectangle> {
    private static final Map<String, Image> cacheOfImages = new HashMap<>();
    private final Card card;
    private Rectangle backElement;

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
        backElement.setStroke(Color.BLACK);
        positionElement(backElement, 0d, 0d);

        CardContentFrame frame = CardContentFrame.get(card, backgroundImage, size);
        positionElement(frame, size / 2d, size * 3d);

        CardHeader header = CardHeader.get(backgroundImage, size * 8, card.getName());
        positionElement(header, 10 + size / 2d, size / 3d);
    }

    @Override
    public CardViewer getElement() {
        return this;
    }
}
