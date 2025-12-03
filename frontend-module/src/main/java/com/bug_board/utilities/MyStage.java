package com.bug_board.utilities;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MyStage extends Stage {

    protected Pane createTitleBar() {
        return TitleBar.createNewTitleBar(this);
    }

    protected void initializeStyleSheet(String cssFileURL, Scene scene) {
        try {
            scene.getStylesheets().add(getClass().getResource(cssFileURL).toExternalForm());
        }
        catch(NullPointerException exc){
            System.err.println(exc.getMessage());
        }
    }
}
