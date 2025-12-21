package com.bug_board.utilities;

import com.bug_board.dto.LabelSummaryDTO;
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
    private Button triggerButton;
    private Popup popup;
    private ScrollPane itemsScrollPane;
    private VBox itemsPane;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private List<Integer> labelsSelected = new ArrayList<>();
    private List<HBox> rows = new ArrayList<>();
    private static final int MAX_NUM_OF_LABELS = 3;

    public DropdownMenu(List<BugBoardLabel> options) {
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
        searchBar.setButtonAction(() -> filterLabel(searchBar.getBarText(), options));
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

    private void filterLabel(String barText, List<BugBoardLabel> label) {

        for(int i = 0; i < label.size(); i++) {
            if(
                label.get(i).getName().toLowerCase().contains(barText.toLowerCase()) ||
                label.get(i).getDescription().toLowerCase().contains(barText.toLowerCase()) ||
                ((CheckBox)rows.get(i).getChildren().getFirst()).isSelected()
            ) {
                rows.get(i).setVisible(true);
                rows.get(i).setManaged(true);
            }
            else {
                rows.get(i).setVisible(false);
                rows.get(i).setManaged(false);
            }
        }
    }

    private void addSelectedLabelId(BugBoardLabel option, Boolean newValue) {
        if(newValue) {
            if (labelsSelected.size() < MAX_NUM_OF_LABELS)
                labelsSelected.add(mapToLabelSummaryDTO(option).getIdLabel());
        }
        else {
            if (labelsSelected.contains(option.getLabelId())) {
                labelsSelected.remove(mapToLabelSummaryDTO(option).getIdLabel());
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
        return labelsSelected;
    }

    //TODO dove fare il mapper???
    private LabelSummaryDTO mapToLabelSummaryDTO(BugBoardLabel bugBoardLabel) {
        LabelSummaryDTO labelSummaryDTO = new LabelSummaryDTO();
        labelSummaryDTO.setIdLabel(bugBoardLabel.getLabelId());
        labelSummaryDTO.setColor(bugBoardLabel.getColor());
        labelSummaryDTO.setName(bugBoardLabel.getName());
        labelSummaryDTO.setDescription(bugBoardLabel.getDescription());

        return labelSummaryDTO;
    }
}