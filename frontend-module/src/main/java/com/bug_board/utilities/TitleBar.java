package com.bug_board.utilities;

import com.bug_board.session_manager.SessionManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TitleBar extends StackPane {
    private int barHeight;
    private Button minimizeButton = new Button();
    private Button closeButton = new Button();
    private Button redimensionButton = new Button();
    private double xOffset;
    private double yOffset;
    private HBox textWrapper = new HBox();
    private Text firstPortionToColor;
    private Text secondPortionToColor;
    private HBox firstLayerTitleBar = new HBox();
    private HBox centralButtonsWrapper = new HBox();

    public TitleBar(Stage parentStage, int height) {
        parentStage.initStyle(StageStyle.TRANSPARENT);

        this.barHeight = height;

        setPropertiesForFirstLayerTitleBar(parentStage);
        setPropertiesForCentralButtonsWrapper();

        this.setFontSize(firstPortionToColor, height/2);
        this.setFontSize(secondPortionToColor, height/2);
        this.setButtons();
        this.getChildren().addAll(firstLayerTitleBar, centralButtonsWrapper);
    }

    private void setPropertiesForCentralButtonsWrapper() {
        centralButtonsWrapper.setAlignment(Pos.CENTER);
        centralButtonsWrapper.prefHeightProperty().bind(this.heightProperty());
        centralButtonsWrapper.setPickOnBounds(false);
    }

    public void addButtonToTitleBar(Button button) {
        button.prefHeightProperty().bind(centralButtonsWrapper.heightProperty());
        centralButtonsWrapper.getChildren().add(button);
    }

    private void setButtons() {
        Image minimizeIcon = new Image(getClass().getResource("/images/hideImage.png").toExternalForm());
        Image closeIcon = new Image(getClass().getResource("/images/closeImage.png").toExternalForm());
        Image redimensionIcon = new Image(getClass().getResource("/images/redimensionImage.png").toExternalForm());

        setButton(minimizeButton, minimizeIcon);
        setButton(redimensionButton, redimensionIcon);
        setButton(closeButton, closeIcon);
    }

    private void setButton(Button buttonToSet, Image buttonsImage){
        ImageView imageView = new ImageView(buttonsImage);
        imageView.setFitHeight(barHeight /3);
        imageView.setFitWidth(barHeight /3);
        buttonToSet.setGraphic(imageView);
        buttonToSet.setMinSize(barHeight /2, barHeight /2);
        buttonToSet.setMaxSize(barHeight /2, barHeight /2);
        buttonToSet.setPrefSize(barHeight /2, barHeight /2);
    }

    private void setPropertiesForFirstLayerTitleBar(Stage parentStage) {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        firstLayerTitleBar.setAlignment(Pos.CENTER_RIGHT);
        firstLayerTitleBar.setPrefSize(parentStage.getWidth(), barHeight);
        firstLayerTitleBar.setId("title-bar");
        setBrandNameForFirstLayerTitleBar();
        firstLayerTitleBar.getChildren().addAll(spacer, minimizeButton, redimensionButton, closeButton);

        setActionPropertiesForFirstLayerTitleBarButtons(this.barHeight);
        setActionPropertiesForFirstLayerTitleBar(parentStage);
    }

    private void setActionPropertiesForFirstLayerTitleBarButtons(int height) {
        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });

        closeButton.setOnAction(e -> {
            SessionManager.getInstance().clearSession();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        redimensionButton.setOnAction(e -> {
            Stage stage = (Stage) redimensionButton.getScene().getWindow();

            if(stage.isResizable())
                if(stage.isMaximized())
                    stage.setMaximized(false);
                else
                    stage.setMaximized(true);
        });
    }

    private void setActionPropertiesForFirstLayerTitleBar(Stage parentStage) {
        this.onMouseClickedProperty().set(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            xOffset = stage.getX() - e.getScreenX();
            yOffset = stage.getX() - e.getScreenX();
        });

        this.onMouseDraggedProperty().set(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });
    }

    private void setBrandNameForFirstLayerTitleBar() {
        firstLayerTitleBar.getChildren().add(
                createTwotoneText("BugBoard26", 3, Color.web("#000000"), Color.web("2AC4AC"))
        );
    }

    private HBox createTwotoneText(String textToColor, int indexFromWhichToSplit, Color firstColor, Color secondColor) {
        firstPortionToColor = new Text(textToColor.substring(0, indexFromWhichToSplit));
        firstPortionToColor.setFill(firstColor);

        secondPortionToColor = new Text(textToColor.substring(indexFromWhichToSplit));
        secondPortionToColor.setFill(secondColor);

        textWrapper.setAlignment(Pos.CENTER_LEFT);
        textWrapper.getChildren().addAll(firstPortionToColor, secondPortionToColor);

        return textWrapper;
    }

    private void setFontSize(Text text, int size){
        String style = "-fx-font-size: " + size + "px;";
        text.setStyle(style);
    }
}
