package com.bug_board.utilities;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyStage extends Stage {

    protected Pane createTitleBar() {
        return new TitleBar(this, 50);
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
