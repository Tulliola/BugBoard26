package com.bug_board.utilities.animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;   
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class RoleRadioButtonAnimation {
    private static final Duration DURATION = Duration.millis(750);

    public static void animate(StackPane container, boolean show) {
        double endOpacity;
        double endHeight;
        if(show){
            endOpacity = 1.0;
            endHeight = Double.MAX_VALUE;
        }
        else{
            endHeight = 0;
            endOpacity = 0.0;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(DURATION,
                        new KeyValue(
                                container.maxHeightProperty(), endHeight
                        )
                ),
                new KeyFrame(
                        DURATION,
                        new KeyValue(
                                container.opacityProperty(), endOpacity
                        )
                )
        );

        timeline.play();
    }
}
