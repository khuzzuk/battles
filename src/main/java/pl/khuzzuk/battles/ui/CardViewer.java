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
class CardViewer extends AnchorPane implements Decorative, Focusable<CardViewer>, Selectable<CardViewer> {
    private static final Map<String, Image> cacheOfImages = new HashMap<>();
    @Getter(AccessLevel.PACKAGE)
    private final Card card;
    @Getter
    @Setter
    private boolean selected;
    @Getter
    private Rectangle backRecktangle;

    static CardViewer instance(Card card, int areaHeight) {
        CardViewer viewer = new CardViewer(card);
        viewer.prepareFrame(cacheOfImages.computeIfAbsent(card.getStyle().getBackgroundPath(), Image::new), areaHeight / 18);
        return viewer;
    }

    private void prepareFrame(Image backgroundImage, int size) {
        backRecktangle = new Rectangle(size * 10, size * 16);
        backRecktangle.setArcWidth(size);
        backRecktangle.setArcHeight(size);
        setBackground(backgroundImage, backRecktangle);
        backRecktangle.setStrokeWidth(1d);
        backRecktangle.setStroke(Color.BLACK);

        CardContentFrame frame = CardContentFrame.get(card, backgroundImage, size);
        AnchorPane.setTopAnchor(frame, size * 3d);
        AnchorPane.setBottomAnchor(frame, size / 2d);
        AnchorPane.setLeftAnchor(frame, size / 2d);
        AnchorPane.setRightAnchor(frame, size / 2d);

        CardHeader header = CardHeader.get(backgroundImage, size * 8, card.getName());
        AnchorPane.setTopAnchor(header, size / 3d);
        AnchorPane.setBottomAnchor(header, frame.getHeight() + 10d);
        AnchorPane.setLeftAnchor(header, 10d + size / 2);
        AnchorPane.setRightAnchor(header, 10d + size / 2);

        getChildren().addAll(backRecktangle, frame, header);
    }

    @Override
    public CardViewer getElement() {
        return this;
    }
}
