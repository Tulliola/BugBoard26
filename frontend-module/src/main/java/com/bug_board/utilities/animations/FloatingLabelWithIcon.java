package com.bug_board.utilities.animations;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class FloatingLabelWithIcon {

    private static final double y_OFFSET  = -22;
    private static final double SCALE_FACTOR = 0.85;
    private static final Duration DURATION = Duration.millis(200);
    private static final Color ACTIVE_COLOR = Color.web("#2AC4AC");
    private static final Color INACTIVE_COLOR = Color.web("#A3A3A3");

    public static StackPane createFloatingLabelField(TextField textField, String placeholder) {

        Label label = new Label(placeholder);
        label.setId("text-field-with-icon");
        label.setTextFill(INACTIVE_COLOR);
        label.setMouseTransparent(true);

        StackPane wrapper = new StackPane();
        wrapper.setAlignment(Pos.CENTER_LEFT);
        wrapper.getChildren().addAll(textField, label);

        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            animateLabel(label, newVal || !textField.getText().isEmpty());
        });

        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !textField.isFocused()) {
                label.setTranslateY(y_OFFSET);
                label.setScaleX(SCALE_FACTOR);
                label.setScaleY(SCALE_FACTOR);
            }
        });

        return wrapper;
    }

    private static void animateLabel(Label label, boolean active) {

        TranslateTransition move = new TranslateTransition(DURATION, label);
        ScaleTransition shrink = new ScaleTransition(DURATION, label);

        if (active) {
            move.setToY(y_OFFSET);
            move.setToX(label.getWidth() * (-0.5) * (1 - SCALE_FACTOR) + 5);

            shrink.setToX(SCALE_FACTOR);
            shrink.setToY(SCALE_FACTOR);

            label.setTextFill(ACTIVE_COLOR);
        } else {
            move.setToY(0);
            move.setToX(0);
            shrink.setToX(1);
            shrink.setToY(1);

            label.setTextFill(INACTIVE_COLOR);
        }

        ParallelTransition parallelTransition = new ParallelTransition(move, shrink);
        parallelTransition.play();
    }
}
