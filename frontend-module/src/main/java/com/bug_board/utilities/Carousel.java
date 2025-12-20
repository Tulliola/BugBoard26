package com.bug_board.utilities;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Carousel extends HBox {
    private final int length;
    private ArrayList<ToggleButton> carouselButtons = new ArrayList<>();
    private ToggleGroup carouselToggleGroup = new ToggleGroup();
    private final int MAX_BUTTONS = 9;

    public Carousel(int buttonsNumber) {
        this.length = buttonsNumber;
        this.setAlignment(Pos.CENTER);
        setCarousel();
    }

    private void setCarousel() {
        this.setToggleGroupLogic();
        setCarouselsButtons();

        if(!carouselButtons.isEmpty())
            carouselButtons.get(0).setSelected(true);
    }

    private void setToggleGroupLogic() {
        carouselToggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal == null && oldVal != null)
                oldVal.setSelected(true);
        });
    }

    private void setCarouselsButtons() {
        this.getChildren().clear();
        carouselButtons.clear();

        if(length >= 1 && length <= 9) {
            for (int i = 0; i < length; i++) {
                this.getChildren().add(this.addButton(i));
            }
        }

        else if(length >= 10) {
            for (int i = 0; i < 4; i++) {
                this.getChildren().add(this.addButton(i));
            }

            Label label = new Label(" ... ");
            this.getChildren().add(label);

            ToggleButton lastButton = new ToggleButton(String.valueOf(length));
            lastButton.getStyleClass().add("carousel-button");
            lastButton.setToggleGroup(carouselToggleGroup);
            this.getChildren().add(lastButton);
        }
    }

    private ToggleButton addButton(int i) {
        ToggleButton carouselButton = new ToggleButton(String.valueOf(i + 1));
        carouselButtons.add(carouselButton);
        carouselButton.setToggleGroup(carouselToggleGroup);
        carouselButton.getStyleClass().add("carousel-button");
        return carouselButton;
    }

    public void setButtonAction(int index, Runnable action) {
        carouselButtons.get(index).setOnMouseClicked(event -> {
            action.run();
        });
    }
}
