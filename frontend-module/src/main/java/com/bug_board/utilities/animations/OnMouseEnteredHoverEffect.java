package com.bug_board.utilities.animations;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class OnMouseEnteredHoverEffect {

    private static final double SCALE_FACTOR = 1.1;
    private static final int DURATION = 200;

    public static void addHoverEffect(Node node) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(25.0);
        dropShadow.setOffsetY(10.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(DURATION), node);
        scaleIn.setToX(SCALE_FACTOR);
        scaleIn.setToY(SCALE_FACTOR);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(DURATION), node);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        node.setOnMouseEntered(e -> {
            node.setViewOrder(-1.0);
            node.setEffect(dropShadow);

            scaleOut.stop();
            scaleIn.playFromStart();
        });

        node.setOnMouseExited(e -> {
            node.setViewOrder(0.0);
            node.setEffect(null);

            scaleIn.stop();
            scaleOut.playFromStart();
        });
    }
}
