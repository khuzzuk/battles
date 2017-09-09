package pl.khuzzuk.battles.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class MenuManager {
    final int menuHeight = 100;
    @NonNull
    private List<Button> menuButtons;

    static MenuManager get() {
        return new MenuManager(new ArrayList<>());
    }

    Button addButton(String title, boolean disabled, List<Node> paneChildren) {
        Button button = new Button(title);
        button.setDisable(disabled);
        double buttonsWidth = menuButtons.stream()
                .mapToDouble(Button::getWidth)
                .sum();
        AnchorPane.setLeftAnchor(button, 10 * (menuButtons.size() + 1) + buttonsWidth);
        AnchorPane.setTopAnchor(button, 10d);
        paneChildren.add(button);
        return button;
    }
}
