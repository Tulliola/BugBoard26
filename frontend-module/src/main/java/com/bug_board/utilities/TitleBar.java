package com.bug_board.utilities;

import com.bug_board.session_manager.SessionManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TitleBar extends HBox {
    private int height;
    private Button minimizeButton = new Button();
    private Button closeButton = new Button();
    private Button redimensionButton = new Button();
    private double xOffset;
    private double yOffset;
    private HBox textWrapper = new HBox();
    private Text firstPortionToColor;
    private Text secondPortionToColor;


    public TitleBar(Stage parentStage, int height) {
        parentStage.initStyle(StageStyle.TRANSPARENT);

        this.height = height;
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        setPropertiesForNewTitleBar(parentStage);
        setCentralBrandNameForNewTitleBar();
        setActionPropertiesForNewTitleBarButtons(this.height);
        setActionPropertiesForNewTitleBar(parentStage);
        this.setFontSize(firstPortionToColor, height/2);
        this.setFontSize(secondPortionToColor, height/2);
        this.setButtons();

        this.getChildren().addAll(spacer, minimizeButton, redimensionButton, closeButton);
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
        imageView.setFitHeight(height/3);
        imageView.setFitWidth(height/3);
        buttonToSet.setGraphic(imageView);
        buttonToSet.setMinSize(height/2, height/2);
        buttonToSet.setMaxSize(height/2, height/2);
        buttonToSet.setPrefSize(height/2, height/2);
    }

    private void setPropertiesForNewTitleBar(Stage parentStage) {
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setPrefSize(parentStage.getWidth(), height);
        this.setId("title-bar");
    }

    private void setActionPropertiesForNewTitleBarButtons(int height) {
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
        });
    }

    private void setActionPropertiesForNewTitleBar(Stage parentStage) {
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

    private void setCentralBrandNameForNewTitleBar() {
        this.getChildren().add(
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
