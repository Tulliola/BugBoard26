package com.bug_board.utilities;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyRadioButton extends RadioButton {
    public MyRadioButton(Image buttonImage) {
        ImageView buttonIcon = new ImageView(buttonImage);

        buttonIcon.setFitWidth(50);
        buttonIcon.setFitHeight(50);
        buttonIcon.setPreserveRatio(true);
        buttonIcon.setSmooth(true);
        buttonIcon.setTranslateX(-4.5);

        this.setGraphic(buttonIcon);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
