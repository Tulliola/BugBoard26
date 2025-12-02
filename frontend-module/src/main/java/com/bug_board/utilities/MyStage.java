package com.bug_board.utilities;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyStage extends Stage {
    private Button minimizeButton;
    private Button closeButton;
    private HBox newTitleBar;

    private double xOffset;
    private double yOffset;

    protected Pane createNewTitleBar(){
        this.initStyle(StageStyle.TRANSPARENT);

        newTitleBar = new HBox();
        minimizeButton = new Button("-");
        closeButton = new Button("x");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setPropertiesForNewTitleBar();
        this.setActionPropertiesForNewTitleBar();
        this.setActionPropertiesForNewTitleBarButtons();

        newTitleBar.getChildren().addAll(spacer, minimizeButton, closeButton);

        return newTitleBar;
    }

    private void setPropertiesForNewTitleBar() {
        newTitleBar.setAlignment(Pos.CENTER_RIGHT);
        newTitleBar.setPadding(new Insets(5, 10, 5, 10));
        newTitleBar.setSpacing(10);
    }

    private void setActionPropertiesForNewTitleBarButtons() {
        minimizeButton.setOnAction(e -> {
            Stage stage =  (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });

        closeButton.setOnAction(e -> {
            Stage stage =  (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void setActionPropertiesForNewTitleBar() {
        newTitleBar.onMouseClickedProperty().set(e -> {
           Stage stage  =  (Stage) newTitleBar.getScene().getWindow();
           xOffset = stage.getX() - e.getScreenX();
           yOffset = stage.getX() - e.getScreenX();
        });

        newTitleBar.onMouseDraggedProperty().set(e -> {
            Stage stage  =  (Stage) newTitleBar.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });
    }
}
