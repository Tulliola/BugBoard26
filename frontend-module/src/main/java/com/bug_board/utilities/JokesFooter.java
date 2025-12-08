package com.bug_board.utilities;

import com.bug_board.utilities.jokes.JokesService;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;

public class JokesFooter extends StackPane {

    private final int numOfJokes;
    private Text scrollingText;
    private TranslateTransition transition;
    private final JokesService jokesService;

    public JokesFooter(int numOfJokes) {
        if(numOfJokes > 10)
            this.numOfJokes = 10;
        else
            this.numOfJokes = numOfJokes;

        jokesService = new JokesService(this.numOfJokes);

        this.getStyleClass().add("jokes-billboard");

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.widthProperty());
        clip.heightProperty().bind(this.heightProperty());
        this.setClip(clip);

        new Thread(this::loadAndStartAnimation).start();
    }

    private void loadAndStartAnimation() {
        ArrayList<String> jokesArray = jokesService.getJokes();

        if (jokesArray == null || jokesArray.isEmpty()) return;

        String fullText = String.join("   •••   ", jokesArray);

        Platform.runLater(() -> {
            setUpText(fullText);

            if (this.getWidth() > 0)
                startAnimation(this.getWidth());
            else
                this.widthProperty().addListener((obs, oldVal, newVal) -> {
                    if (transition == null && newVal.doubleValue() > 0) {
                        startAnimation(newVal.doubleValue());
                    }
                });
        });
    }

    private void setUpText(String text) {
        scrollingText = new Text(text);
        scrollingText.setFont(Font.font("Poppins", FontWeight.BOLD, 40));
        scrollingText.setFill(Color.web("#FFFFFF"));

        this.getChildren().add(scrollingText);
    }

    private void startAnimation(double containerWidth) {
        if (transition != null) transition.stop();

        scrollingText.setTranslateX(containerWidth);

        transition = new TranslateTransition();
        transition.setNode(scrollingText);

        double textWidth = scrollingText.getLayoutBounds().getWidth();
        double totalDistance = containerWidth + textWidth;

        transition.setDuration(Duration.seconds(totalDistance / 150));
        transition.setFromX(containerWidth);
        transition.setToX(-textWidth);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(Animation.INDEFINITE);

        transition.play();
    }
}