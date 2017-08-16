package pl.khuzzuk.battles.ui;

import groovy.transform.CompileStatic;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@CompileStatic
public class CardViewer extends AnchorPane {
    private static final Map<String, Image> cacheOfImages = new HashMap<String, Image>();
    private CardContentFrame frame;
    private CardHeader header;

    public static CardViewer instance(String backgroundPath) {
        CardViewer viewer = new CardViewer();
        return viewer.prepareFrame(cacheOfImages.computeIfAbsent(backgroundPath, Image::new));
    }

    private CardViewer prepareFrame(Image backgroundImage) {
        this.frame = CardContentFrame.get(backgroundImage, 25);
        AnchorPane.setTopAnchor(frame, 100d);
        AnchorPane.setBottomAnchor(frame, 10d);
        AnchorPane.setLeftAnchor(frame, 10d);
        AnchorPane.setRightAnchor(frame, 10d);

        this.header = CardHeader.get();
        AnchorPane.setTopAnchor(header, 10d);
        AnchorPane.setBottomAnchor(header, 100d);
        AnchorPane.setLeftAnchor(header, 10d);
        AnchorPane.setRightAnchor(header, 10d);

        getChildren().addAll(frame);
        return this;
    }
}
