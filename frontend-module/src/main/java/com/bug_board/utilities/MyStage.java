package com.bug_board.utilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MyStage extends Stage {

    protected MyStage() {
        this.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_bugboard.png")));
    }

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

    protected void setFullScreen(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        this.setX(screenBounds.getMinX());
        this.setY(screenBounds.getMinY());
        this.setWidth(screenBounds.getWidth());
        this.setHeight(screenBounds.getHeight());
    }
}
