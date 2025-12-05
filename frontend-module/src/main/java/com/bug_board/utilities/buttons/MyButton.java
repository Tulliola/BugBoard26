package com.bug_board.utilities.buttons;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class MyButton extends Button {
    public MyButton() {
        this.setCursor(Cursor.HAND);
    }

    public MyButton(String text) {
        super(text);
        this.setCursor(Cursor.HAND);
    }
}
