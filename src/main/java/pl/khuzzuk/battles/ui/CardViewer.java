package pl.khuzzuk.battles.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.khuzzuk.battles.cards.Card;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CardViewer extends AnchorPane implements Decorative {
    private static final Map<String, Image> cacheOfImages = new HashMap<String, Image>();
    private final Card card;
    private CardContentFrame frame;
    private CardHeader header;

    public static CardViewer instance(Card card) {
        CardViewer viewer = new CardViewer(card);
        return viewer.prepareFrame(cacheOfImages.computeIfAbsent(card.getStyle().getBackgroundPath(), Image::new), 25);
    }

    private CardViewer prepareFrame(Image backgroundImage, int size) {
        Rectangle background = new Rectangle(size * 10, size * 16);
        background.setArcWidth(size);
        background.setArcHeight(size);
        setBackground(backgroundImage, background);
        background.setStrokeWidth(1d);
        background.setStroke(Color.BLACK);

        this.frame = CardContentFrame.get(card, backgroundImage, size);
        AnchorPane.setTopAnchor(frame, size * 3d);
        AnchorPane.setBottomAnchor(frame, size / 2d);
        AnchorPane.setLeftAnchor(frame, size / 2d);
        AnchorPane.setRightAnchor(frame, size / 2d);

        this.header = CardHeader.get(backgroundImage, size * 8, "Card");
        AnchorPane.setTopAnchor(header, size / 3d);
        AnchorPane.setBottomAnchor(header, frame.getHeight() + 10d);
        AnchorPane.setLeftAnchor(header, 10d + size / 2);
        AnchorPane.setRightAnchor(header, 10d + size / 2);

        getChildren().addAll(background, frame, header);
        //setBackground(new Background(new BackgroundImage(
          //      backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));
        return this;
    }
}
