package com.bug_board.utilities;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MySpacer {
    public static Label createHSpacer(){
        Label spacer = new Label();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return spacer;
    }

    public static Label createVSpacer(){
        Label spacer = new Label();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        return spacer;
    }
}
