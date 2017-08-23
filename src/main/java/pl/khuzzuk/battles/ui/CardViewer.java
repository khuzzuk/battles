package pl.khuzzuk.battles.ui;

import groovy.transform.CompileStatic;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@CompileStatic
public class CardViewer extends AnchorPane implements Decorative {
    private static final Map<String, Image> cacheOfImages = new HashMap<String, Image>();
    private CardContentFrame frame;
    private CardHeader header;

    public static CardViewer instance(String backgroundPath) {
        CardViewer viewer = new CardViewer();
        return viewer.prepareFrame(cacheOfImages.computeIfAbsent(backgroundPath, Image::new));
    }

    private CardViewer prepareFrame(Image backgroundImage) {
        int size = 25;
        this.frame = CardContentFrame.get(backgroundImage, size);
        AnchorPane.setTopAnchor(frame, 100d);
        AnchorPane.setBottomAnchor(frame, 10d);
        AnchorPane.setLeftAnchor(frame, 10d);
        AnchorPane.setRightAnchor(frame, 10d);

        this.header = CardHeader.get(backgroundImage, size * 8, "Card");
        AnchorPane.setTopAnchor(header, 10d);
        AnchorPane.setBottomAnchor(header, frame.getHeight() + 10d);
        AnchorPane.setLeftAnchor(header, 10d + size / 2);
        AnchorPane.setRightAnchor(header, 10d);

        getChildren().addAll(frame, header);
        setBackground(new Background(new BackgroundImage(
                backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, null)));
        return this;
    }
}
