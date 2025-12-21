package com.bug_board.utilities.animations;

import com.bug_board.utilities.SearchBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class AnimatedSearchBar extends SearchBar {

    private boolean isExpanded = false;

    public AnimatedSearchBar() {
        super();
        container.setAlignment(Pos.CENTER_RIGHT);
        searchField.setVisible(false);
        searchField.setManaged(false);
    }

    public void animate(){
        searchField.setManaged(true);
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(300),
                        new KeyValue(searchField.prefWidthProperty(), 250.0)
                ));

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                new KeyValue(searchField.opacityProperty(), 1.0)
        ));

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(300),
                        new KeyValue(searchButton.translateXProperty(), -10.0)
                )
        );

        timeline.setOnFinished(e -> {
            searchField.setVisible(true);
            isExpanded = true;
            searchField.setPrefWidth(250.0);
            searchField.setOpacity(1.0);
            searchField.setTranslateX(0.0);
        });


        timeline.play();
    }

    public void setButtonAction(Runnable action){
        searchButton.setOnAction(e -> {
            if(!isExpanded){
                animate();
                isExpanded = true;
            }
            else
                action.run();
        });
    }
}
