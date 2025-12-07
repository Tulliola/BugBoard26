package com.bug_board.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConfirmTransactionPane extends VBox {
    private String confirmationMessage;
    private String gifURL;

    public ConfirmTransactionPane(String gifURL, String confirmationMessage) {
        this.gifURL = gifURL;
        this.confirmationMessage = confirmationMessage;

        initialize();
    }

    private void initialize() {
        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                this.createGif(),
                this.createConfirmationMessage()
        );
    }

    private ImageView createGif() {
        ImageView gif = new ImageView(new Image(getClass().getResourceAsStream(gifURL)));
        gif.setFitHeight(100);
        gif.setFitWidth(100);

        return gif;
    }

    private Text createConfirmationMessage() {
        Text message = new Text(confirmationMessage);
        message.setStyle("-fx-fill: " +
                "linear-gradient(" +
                "to right, -color-primary, white); -fx-font-size: 40px");

        return message;
    }
}
