package pl.khuzzuk.battles.ui

import groovy.transform.CompileStatic
import javafx.scene.layout.AnchorPane
import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@CompileStatic
class CardViewer extends AnchorPane {
    CardContentFrame frame;
    CardHeader header;

    static CardViewer instance() {
        CardViewer viewer = new CardViewer()
        viewer.prepareFrame()
    }

    CardViewer prepareFrame() {
        this.frame = CardContentFrame.get()
        setTopAnchor(frame, 100d)
        setBottomAnchor(frame, 10d)
        setLeftAnchor(frame, 10d)
        setRightAnchor(frame, 10d)

        this.header = CardHeader.get()
        setTopAnchor(header, 10d)
        setBottomAnchor(header, 100d)
        setLeftAnchor(header, 10d)
        setRightAnchor(header, 10d)

        getChildren().addAll(frame, header)
        this
    }
}
