package com.bug_board.utilities;

import com.bug_board.dto.LabelSummaryDTO;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private List<CheckBox> checkedBoxes = new ArrayList<>();
    private static final int MAX_NUM_OF_LABELS = 3;

    public DropdownMenu(List<BugBoardLabel> options) {
        triggerButton = new Button("Select up to 3 labels");
        triggerButton.setId("popup-button");
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
        itemsPane.setStyle("-fx-background-color: white; -fx-border-color: -color-primary; -fx-border-width: 2px; -fx-min-width: 350px");

        for (BugBoardLabel option : options) {
            option.setPadding(new Insets(0, 0, 0, 10));
            CheckBox labelCheckBox = new CheckBox();

            labelCheckBox.setGraphic(option);
            labelCheckBox.getStyleClass().add("bugboard-checkbox");
            labelCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                this.setLabelsCeiling(labelCheckBox, newValue);
            });

            HBox row = new HBox();
            row.getStyleClass().add("dropdown-row");
            labelCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                row.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), isSelected);
            });
            row.setOnMouseClicked(e -> {
                if (e.getTarget() != labelCheckBox && e.getTarget() != labelCheckBox.lookup(".box")) {
                    labelCheckBox.setSelected(!labelCheckBox.isSelected());
                    labelCheckBox.fire();
                }
            });

            checkBoxes.add(labelCheckBox);
            row.getChildren().add(labelCheckBox);
            itemsPane.getChildren().add(row);
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

    private void setLabelsCeiling(CheckBox labelCheckBox, Boolean isSelected) {
        if(isSelected) {
            long count = 0;
            for(CheckBox checkBox : checkBoxes) {
                if(checkBox.isSelected())
                    count++;
            }

            if(count > MAX_NUM_OF_LABELS)
                labelCheckBox.setSelected(false);
        }
        else
            labelCheckBox.setSelected(false);
    }
}