package com.bug_board.views;

import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class HomePageView extends MyStage {
    VBox vBox = new VBox();
    Scene scene = new Scene(vBox);

    public HomePageView() {
        this.initialize();
    }

    private void initialize(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        this.setX(screenBounds.getMinX());
        this.setY(screenBounds.getMinY());
        this.setWidth(screenBounds.getWidth());
        this.setHeight(screenBounds.getHeight());
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());
        this.setScene(scene);
        this.setMaximized(true);
        vBox.getChildren().add(new TitleBar(this, 80));
    }
}
