package com.bug_board.utilities;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Carousel extends HBox {
    private final int length;
    private ArrayList<ToggleButton> carouselButtons = new ArrayList<>();
    private ToggleGroup carouselToggleGroup = new ToggleGroup();

    public Carousel(int buttonsNumber) {
        this.length = buttonsNumber;
        this.setAlignment(Pos.CENTER);
        setCarousel();
    }

    private void setCarousel() {
        this.setToggleGroupLogic();
        this.getChildren().addAll(setCarouselsButtons());

        if(!carouselButtons.isEmpty())
            carouselButtons.get(0).setSelected(true);
    }

    private void setToggleGroupLogic() {
        carouselToggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal == null && oldVal != null)
                oldVal.setSelected(true);
        });
    }

    private List<ToggleButton> setCarouselsButtons() {
        for (int i = 0; i < length; i++) {
            ToggleButton carouselButton = new ToggleButton(String.valueOf(i + 1));
            carouselButtons.add(carouselButton);
            carouselButton.setToggleGroup(carouselToggleGroup);
            carouselButton.getStyleClass().add("carousel-button");
        }

        return carouselButtons;
    }

    public void setButtonAction(int index, Runnable action) {
        carouselButtons.get(index).setOnMouseClicked(event -> {
            action.run();
        });
    }
}
