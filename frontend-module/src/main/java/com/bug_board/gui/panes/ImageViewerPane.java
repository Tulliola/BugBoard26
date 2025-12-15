package com.bug_board.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

public class ImageViewerPane extends StackPane {

    private final byte[] imageToShow;
    private final StackPane parentContainer;

    private final static int IMAGE_BOX_WIDTH = 1200;
    private final static int IMAGE_BOX_HEIGHT = 900;

    public ImageViewerPane(StackPane parentContainer,
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
        imageBox.setPrefSize(IMAGE_BOX_WIDTH, IMAGE_BOX_HEIGHT);
        imageBox.setMaxSize(IMAGE_BOX_WIDTH, IMAGE_BOX_HEIGHT);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: white");

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageToShow)));
        imageView.setFitWidth(IMAGE_BOX_WIDTH);
        imageView.setFitHeight(IMAGE_BOX_HEIGHT);

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
