package com.bug_board.utilities;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

public class DropdownMenu extends VBox {
    private Button triggerButton;
    private Popup popup = new Popup();
    private VBox itemsPane = new VBox();
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public DropdownMenu(List<String> options) {
        this.checkBoxes = new ArrayList<>();

        triggerButton = new Button("Seleziona etichette");
        triggerButton.setMaxWidth(Double.MAX_VALUE);
        triggerButton.setAlignment(Pos.CENTER_LEFT);

        SVGPath arrow = new SVGPath();
        arrow.setContent("M7 10l5 5 5-5z");
        arrow.setStyle("-fx-fill: #555;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox graphicBox = new HBox(spacer, arrow);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        triggerButton.setGraphic(graphicBox);
        triggerButton.setContentDisplay(ContentDisplay.RIGHT);


        popup = new Popup();
        popup.setAutoHide(true);

        itemsPane = new VBox(5);
        itemsPane.setPadding(new Insets(10));
        itemsPane.getStyleClass().add("custom-dropdown-popup");

        for (String option : options) {
            CheckBox cb = new CheckBox(option);
            cb.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

            cb.setOnAction(e -> {});

            checkBoxes.add(cb);
            itemsPane.getChildren().add(cb);
        }

        ScrollPane scrollPane = new ScrollPane(itemsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(200);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("custom-dropdown-scroll");

        popup.getContent().add(scrollPane);

        triggerButton.setOnAction(e -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                Bounds bounds = triggerButton.localToScreen(triggerButton.getBoundsInLocal());
                scrollPane.setMinWidth(bounds.getWidth());
                popup.show(triggerButton, bounds.getMinX(), bounds.getMaxY());
            }
        });

        this.getChildren().add(triggerButton);
    }
}
