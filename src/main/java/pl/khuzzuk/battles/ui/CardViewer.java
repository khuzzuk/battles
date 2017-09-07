package pl.khuzzuk.battles.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class CardViewer extends AnchorPane implements Decorative {
    private static final Map<String, Image> cacheOfImages = new HashMap<>();
    @Getter(AccessLevel.PACKAGE)
    private final Card card;

    static CardViewer instance(Card card, int areaHeight) {
        CardViewer viewer = new CardViewer(card);
        viewer.prepareFrame(cacheOfImages.computeIfAbsent(card.getStyle().getBackgroundPath(), Image::new), areaHeight / 18);
        return viewer;
    }

    private void prepareFrame(Image backgroundImage, int size) {
        Rectangle background = new Rectangle(size * 10, size * 16);
        background.setArcWidth(size);
        background.setArcHeight(size);
        setBackground(backgroundImage, background);
        background.setStrokeWidth(1d);
        background.setStroke(Color.BLACK);

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

        getChildren().addAll(background, frame, header);
    }
}
