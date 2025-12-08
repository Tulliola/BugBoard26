package com.bug_board.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TransactionPane extends VBox {
    private String transactionResponse;
    private String gifURL;
    private Text message;

    public TransactionPane(String gifURL, String transactionResponse) {
        this.gifURL = gifURL;
        this.transactionResponse = transactionResponse;

        initialize();
    }

    private void initialize() {
        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                this.createGif(),
                this.createTransactionMessage()
        );
    }

    private ImageView createGif() {
        ImageView gif = new ImageView(new Image(getClass().getResourceAsStream(gifURL)));
        gif.setFitHeight(100);
        gif.setFitWidth(100);

        return gif;
    }

    private Text createTransactionMessage() {
        message = new Text(transactionResponse);
        message.setStyle("-fx-fill: " +
                "linear-gradient(" +
                "to right, -color-primary, white); -fx-font-size: 40px");

        return message;
    }

    public void setErrorGradient() {
        String errorStyle = "-fx-fill: linear-gradient( to right, red, white ); -fx-font-size: 40px";
        message.setStyle(errorStyle);
    }
}
