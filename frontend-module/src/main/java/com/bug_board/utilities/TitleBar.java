package com.bug_board.utilities;

import com.bug_board.session_manager.SessionManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TitleBar {
    private static int height = 40;
    private static HBox newTitleBar;
    private static Button minimizeButton;
    private static Button closeButton;
    private static double xOffset;
    private static double yOffset;

    public static Pane createNewTitleBar(Stage parentStage){
        parentStage.initStyle(StageStyle.TRANSPARENT);

        newTitleBar = new HBox();
        minimizeButton = new Button("-");
        closeButton = new Button("x");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        setPropertiesForNewTitleBar(parentStage);
        setCentralBrandNameForNewTitleBar();
        setActionPropertiesForNewTitleBar(parentStage);
        setActionPropertiesForNewTitleBarButtons(parentStage);

        newTitleBar.getChildren().addAll(spacer, minimizeButton, closeButton);

        return newTitleBar;
    }

    private static void setPropertiesForNewTitleBar(Stage parentStage) {
        newTitleBar.setAlignment(Pos.CENTER_RIGHT);
        newTitleBar.setPrefSize(parentStage.getWidth(), 50);
        newTitleBar.setId("title-bar");
    }

    private static void setActionPropertiesForNewTitleBarButtons(Stage parentStage) {
        minimizeButton.setPrefSize(35, 35);
        minimizeButton.setOnAction(e -> {
            Stage stage =  (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });

        closeButton.setPrefSize(35, 35);
        closeButton.setOnAction(e -> {
            SessionManager.getInstance().clearSession();
            Stage stage =  (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private static void setActionPropertiesForNewTitleBar(Stage parentStage) {
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

    private static void setCentralBrandNameForNewTitleBar() {
        newTitleBar.getChildren().add(
                createTwotoneText("BugBoard26", 3, Color.web("#000000"), Color.web("2AC4AC"))
        );
    }

    private static HBox createTwotoneText(String textToColor, int indexFromWhichToSplit, Color firstColor, Color secondColor){
        Text firstPortionToColor = new Text(textToColor.substring(0, indexFromWhichToSplit));
        firstPortionToColor.setFill(firstColor);

        Text secondPortionToColor = new Text(textToColor.substring(indexFromWhichToSplit));
        secondPortionToColor.setFill(secondColor);

        HBox textWrapper = new HBox();
        textWrapper.setAlignment(Pos.CENTER_LEFT);
        textWrapper.getChildren().addAll(firstPortionToColor, secondPortionToColor);

        return textWrapper;
    }
}
