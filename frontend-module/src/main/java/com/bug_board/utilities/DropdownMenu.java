package com.bug_board.utilities;

import javafx.css.PseudoClass;
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
    private List<BugBoardLabel> options;
    private Popup popup;
    private ScrollPane itemsScrollPane;
    private VBox itemsPane;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private List<Integer> labelsSelectedId = new ArrayList<>();
    private List<HBox> rows = new ArrayList<>();
    private static final int MAX_NUM_OF_LABELS = 3;

    public DropdownMenu(List<BugBoardLabel> options) {
        this.options = options;
        setTriggerButtonProperties();
        setContent(options);
    }

    private void setContent(List<BugBoardLabel> options) {
        popup = new Popup();
        popup.setAutoHide(true);

        itemsPane = new VBox(5);
        itemsPane.setPadding(new Insets(10));

        for (BugBoardLabel option : options) {
            addLabel(option);
        }

        SearchBar searchBar = new SearchBar();

        searchBar.setTextFieldPrompt("Search Label");
        searchBar.setSearchButtonAction(() -> filterLabel(searchBar.getBarText()));
        searchBar.setClearButtonAction(() -> filterLabel(""));
        searchBar.setPadding(new Insets(10));

        itemsScrollPane = new ScrollPane(itemsPane);
        itemsScrollPane.setFitToWidth(true);
        itemsScrollPane.setMaxHeight(200);
        itemsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        itemsScrollPane.getStyleClass().add("custom-dropdown-scroll");

        VBox searchBarAndLabelsWrapper = new VBox();
        searchBarAndLabelsWrapper.getChildren().addAll(searchBar, itemsScrollPane);
        searchBarAndLabelsWrapper.setAlignment(Pos.TOP_CENTER);
        searchBarAndLabelsWrapper.setStyle("-fx-background-color: white; -fx-border-color: -color-primary; -fx-border-width: 2px; -fx-min-width: 350px");

        popup.getContent().add(searchBarAndLabelsWrapper);
    }

    private void addLabel(BugBoardLabel option) {
        option.setPadding(new Insets(0, 0, 0, 10));
        CheckBox labelCheckBox = new CheckBox();

        labelCheckBox.setGraphic(option);
        labelCheckBox.getStyleClass().add("bugboard-checkbox");
        labelCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.setAndCheckLabelsCeiling(labelCheckBox, newValue);
            addSelectedLabelId(option, newValue);
        });

        HBox row = new HBox();
        row.getStyleClass().add("dropdown-row");
        rows.add(row);

        labelCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
             row.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), isSelected);
        });

        checkBoxes.add(labelCheckBox);

        row.getChildren().add(labelCheckBox);

        itemsPane.getChildren().add(row);
    }

    private void setTriggerButtonProperties() {
        Button triggerButton = new Button("Select up to 3 labels");
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

        triggerButton.setOnAction(e -> {
            if (popup.isShowing()) {
                popup.hide();
            }
            else {
                Bounds bounds = triggerButton.localToScreen(triggerButton.getBoundsInLocal());
                itemsScrollPane.setMinWidth(bounds.getWidth());
                popup.show(triggerButton, bounds.getMinX(), bounds.getMaxY());
            }
        });

        this.getChildren().add(triggerButton);
    }

    private void filterLabel(String barText) {
        for(int i = 0; i < options.size(); i++) {
            if(
                options.get(i).getName().toLowerCase().contains(barText.toLowerCase()) ||
                options.get(i).getDescription().toLowerCase().contains(barText.toLowerCase()) ||
                checkBoxes.get(i).isSelected()
            ) {
                rows.get(i).setVisible(true);
                rows.get(i).setManaged(true);
            }
            else {
                rows.get(i).setVisible(false);
                rows.get(i).setManaged(false);
            }
        }
        reorderRows();
    }

    private void reorderRows() {
        List<HBox> reorderedRows = new ArrayList<>();
        List<CheckBox> reorderedCheckBoxes = new ArrayList<>();
        List<BugBoardLabel> reorderedLabels = new ArrayList<>();

        for(int i = 0; i < checkBoxes.size(); i++) {
            if(checkBoxes.get(i).isSelected()) {
                reorderedLabels.add(options.get(i));
                reorderedCheckBoxes.add(checkBoxes.get(i));
                reorderedRows.add(rows.get(i));
            }
        }

        for(int i = 0; i < checkBoxes.size(); i++) {
            if(!checkBoxes.get(i).isSelected()) {
                reorderedLabels.add(options.get(i));
                reorderedCheckBoxes.add(checkBoxes.get(i));
                reorderedRows.add(rows.get(i));
            }
        }

        rows = reorderedRows;
        checkBoxes = reorderedCheckBoxes;
        options = reorderedLabels;

        itemsPane.getChildren().removeAll(rows);
        itemsPane.getChildren().addAll(rows);
    }

    private void addSelectedLabelId(BugBoardLabel option, Boolean isSelected) {
        if(isSelected) {
            if (labelsSelectedId.size() < MAX_NUM_OF_LABELS)
                labelsSelectedId.add(option.getLabelId());
        }
        else {
            if (labelsSelectedId.contains(option.getLabelId())) {
                labelsSelectedId.remove(option.getLabelId());
            }
        }
    }

    private void setAndCheckLabelsCeiling(CheckBox labelCheckBox, Boolean isSelected) {
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

    public List<Integer> getSelectedLabels() {
        return labelsSelectedId;
    }
}