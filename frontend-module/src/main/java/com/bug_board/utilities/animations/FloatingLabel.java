package com.bug_board.utilities.animations;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class FloatingLabel {

    private static final double Y_OFFSET_TEXTFIELD = -25;
    private static final double Y_OFFSET_TEXTAREA = -25;
    private static final double SCALE_FACTOR = 0.85;
    private static final Duration DURATION = Duration.millis(200);
    private static final Color ACTIVE_COLOR = Color.web("#2AC4AC");
    private static final Color INACTIVE_COLOR = Color.web("#A3A3A3");

    public static StackPane createFloatingLabelField(TextInputControl inputControl, String placeholder) {

        Label label = new Label(placeholder);
        label.setId("text-field-with-icon");
        label.setTextFill(INACTIVE_COLOR);
        label.setMouseTransparent(true);

        label.setTranslateX(10);

        boolean isTextArea = inputControl instanceof TextArea;

        StackPane wrapper = new StackPane();
        wrapper.setAlignment(isTextArea ? Pos.TOP_LEFT : Pos.CENTER_LEFT);
        wrapper.getChildren().addAll(inputControl, label);

        inputControl.focusedProperty().addListener((obs, oldVal, newVal) -> {
            animateLabel(label, newVal || !inputControl.getText().isEmpty(), isTextArea);
        });

        inputControl.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !inputControl.isFocused()) {
                setLabelActiveState(label, isTextArea);
            }
        });

        return wrapper;
    }

    private static void animateLabel(Label label, boolean active, boolean isTextArea) {

        TranslateTransition move = new TranslateTransition(DURATION, label);
        ScaleTransition shrink = new ScaleTransition(DURATION, label);

        double targetY = isTextArea ? Y_OFFSET_TEXTAREA : Y_OFFSET_TEXTFIELD;
        double targetX = label.getWidth() * -0.5 * (1 - SCALE_FACTOR);

        if (active) {
            move.setToY(targetY);
            move.setToX(targetX + 5);

            shrink.setToX(SCALE_FACTOR);
            shrink.setToY(SCALE_FACTOR);

            label.setTextFill(ACTIVE_COLOR);
        } else {
            move.setToY(isTextArea ? 10 : 0);
            move.setToX(0);
            shrink.setToX(1);
            shrink.setToY(1);

            label.setTextFill(INACTIVE_COLOR);
        }

        ParallelTransition parallelTransition = new ParallelTransition(move, shrink);
        parallelTransition.play();
    }

    private static void setLabelActiveState(Label label, boolean isTextArea) {
        double targetY = isTextArea ? Y_OFFSET_TEXTAREA : Y_OFFSET_TEXTFIELD;
        double targetX = label.getWidth() * -0.5 * (1 - SCALE_FACTOR);

        label.setTranslateY(targetY);
        label.setTranslateX(targetX);
        label.setScaleX(SCALE_FACTOR);
        label.setScaleY(SCALE_FACTOR);
    }
}
