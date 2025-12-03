package com.bug_board.utilities;

import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MyStage extends Stage {

    protected Pane createTitleBar() {
        return TitleBar.createNewTitleBar(this);
    }
}
