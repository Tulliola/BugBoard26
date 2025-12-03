package com.bug_board.utilities.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class TextTypingEffect {
    private final ArrayList<String> wordsToShow = new ArrayList<String>();
    private final Text textToAnimate;

    final IntegerProperty currCharIndex = new SimpleIntegerProperty(0);
    private int currShowedWordIndex = 0;

    private Timeline typingTimeLine;
    private Timeline deletingTimeLine;

    public TextTypingEffect(Text textToAnimate, String... wordsToShow) {
        this.textToAnimate = textToAnimate;
        this.wordsToShow.addAll(Arrays.asList(wordsToShow));
    }

    public void startAnimation() {
        if(!wordsToShow.isEmpty())
            startTyping();
    }

    private void startTyping() {
        String currentWord = wordsToShow.get(currShowedWordIndex);
        currCharIndex.set(0);

        typingTimeLine = new Timeline(
                new KeyFrame(Duration.millis(70), event -> {
                    if (currCharIndex.get() < currentWord.length()) {
                        String newWord = currentWord.substring(0, currCharIndex.get() + 1);
                        textToAnimate.setText(newWord);
                        currCharIndex.set(currCharIndex.get() + 1);
                    } else {
                        this.typingTimeLine.stop();
                        pauseAndThenStartDeleting();
                    }
                })
        );

        typingTimeLine.setCycleCount(Timeline.INDEFINITE);
        typingTimeLine.play();
    }

    private void pauseAndThenStartDeleting() {
        Timeline pauseAndDeleteTimeline = new Timeline(
            new KeyFrame(Duration.millis(1500), event -> {
                startDeleting();
            })
        );

        pauseAndDeleteTimeline.setCycleCount(1);
        pauseAndDeleteTimeline.play();
    }

    private void startDeleting() {
        String currentWord  = wordsToShow.get(currShowedWordIndex);
        currCharIndex.set(currentWord.length());

        deletingTimeLine = new Timeline(
                new KeyFrame(Duration.millis(70), event -> {
                    if (currCharIndex.get() >= 0) {
                        String newWord = currentWord.substring(0, currCharIndex.get());
                        textToAnimate.setText(newWord);
                        currCharIndex.set(currCharIndex.get() - 1);
                    } else {
                        this.deletingTimeLine.stop();
                        currShowedWordIndex = (currShowedWordIndex + 1) % wordsToShow.size();
                        startTyping();
                    }
                })
        );

        deletingTimeLine.setCycleCount(Timeline.INDEFINITE);
        deletingTimeLine.play();
    }
}
