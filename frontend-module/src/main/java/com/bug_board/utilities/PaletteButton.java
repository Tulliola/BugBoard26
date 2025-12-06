package com.bug_board.utilities;

import javafx.scene.control.Button;

public class PaletteButton extends Button {
    private final String color;

    public PaletteButton(String color) {
        this.color = color;

        this.initialize();
    }

    private void initialize() {
        this.getStyleClass().add("palette-button");
        this.setStyle("-fx-background-color: " + color);
    }

    public String getColor() {
        return color;
    }
}
