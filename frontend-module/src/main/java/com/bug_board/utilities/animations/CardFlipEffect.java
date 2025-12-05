package com.bug_board.utilities.animations;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CardFlipEffect {
    private final Pane frontCard;
    private final Pane backCard;
    private final Pane cardToRotate;
    private boolean isFrontVisible = true;
    private Duration duration = Duration.millis(750);

    public CardFlipEffect(Pane frontCard, Pane backCard, Pane cardToRotate) {
        this.frontCard = frontCard;
        this.backCard = backCard;
        this.cardToRotate = cardToRotate;

        this.backCard.setVisible(false);
        this.frontCard.setVisible(true);

        this.backCard.setMouseTransparent(true);
        this.frontCard.setMouseTransparent(false);

        this.backCard.setRotate(0);
        this.frontCard.setRotate(0);
        this.cardToRotate.setRotationAxis(Rotate.Y_AXIS);
    }

    public void handleFlip() {
        if (frontCard == null || backCard == null || cardToRotate == null)
            throw new RuntimeException("You must pass not null arguments first.");

        if (isFrontVisible)
            performFlip(frontCard, backCard, 0, 90, -90, 0);
        else
            performFlip(backCard, frontCard, 0, -90, 90, 0);

        isFrontVisible = !isFrontVisible;
    }

    private void performFlip(Node nodeToHide, Node nodeToShow, double start1, double end1, double start2, double end2){

        RotateTransition rotate1 = new RotateTransition(duration.divide(2), cardToRotate);
        rotate1.setAxis(Rotate.Y_AXIS);
        rotate1.setFromAngle(start1);
        rotate1.setToAngle(end1);
        rotate1.setInterpolator(Interpolator.EASE_IN);

        rotate1.setOnFinished(e -> {

            nodeToHide.setVisible(false);
            nodeToShow.setVisible(true);

            nodeToHide.setMouseTransparent(true);
            nodeToShow.setMouseTransparent(false);

            cardToRotate.setRotate(start2);

            RotateTransition rotate2 = new RotateTransition(duration.divide(2), cardToRotate);
            rotate2.setAxis(Rotate.Y_AXIS);
            rotate2.setFromAngle(start2);
            rotate2.setToAngle(end2);
            rotate2.setInterpolator(Interpolator.EASE_OUT);

            rotate2.play();
        });

        rotate1.play();
    }
}
