package com.bug_board.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

public class ImageViewPane extends StackPane {

    private final byte[] imageToShow;
    private final StackPane parentContainer;

    public ImageViewPane(StackPane parentContainer,
                            byte[] imageToShow) {
        this.parentContainer = parentContainer;
        this.imageToShow = imageToShow;

        this.initalize();
    }

    private void initalize() {
        setImageBox();
        setBackground();
    }

    private void setImageBox() {
        VBox imageBox = new VBox();
        imageBox.setPrefSize(500, 500);
        imageBox.setMaxSize(500, 500);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: white");

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageToShow)));
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);

        imageBox.getChildren().add(imageView);

        this.getChildren().add(imageBox);
        StackPane.setAlignment(imageBox, Pos.CENTER);
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                close();
        });
    }

    public void close() {
        parentContainer.getChildren().remove(this);
    }
}
