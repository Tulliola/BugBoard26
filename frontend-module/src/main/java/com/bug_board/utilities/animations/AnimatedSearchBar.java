package com.bug_board.utilities.animations;

import com.bug_board.utilities.SearchBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class AnimatedSearchBar extends SearchBar {

    public AnimatedSearchBar() {
        super();
        container.setAlignment(searchButton, Pos.CENTER);
        searchField.setVisible(false);
    }

    public void animate(){
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

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                new KeyValue(searchField.translateXProperty(), 0.0)
        ));

        timeline.setOnFinished(e -> {
            searchField.setVisible(true);
            isExpanded = true;
            searchField.setPrefWidth(400.0);
            searchField.setOpacity(1.0);
            searchField.setTranslateX(0.0);
        });

        container.setAlignment(searchButton, Pos.CENTER_RIGHT);

        timeline.play();
    }

    @Override
    protected void setButton(){
        super.setButton();
        searchButton.setOnAction(e -> {
            if(!isExpanded){
                animate();
            }
        });
    }
}
