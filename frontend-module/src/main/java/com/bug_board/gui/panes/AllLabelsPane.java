package com.bug_board.gui.panes;

import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.utilities.BugBoardLabel;
import com.bug_board.utilities.SearchBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class AllLabelsPane extends StackPane {

    private final LabelManagementPC labelManagementPC;
    private final StackPane parentContainer;

    private VBox labelsContainer;

    private static final int PREF_WIDTH = 600;
    private static final int PREF_HEIGHT = 800;

    public AllLabelsPane(LabelManagementPC labelManagementPC,
                         StackPane parentContainer) {
        this.labelManagementPC = labelManagementPC;
        labelManagementPC.setVisualizationPane(this);
        this.parentContainer = parentContainer;

        initialize();
    }

    private void initialize() {
        this.setBackground();

        this.getChildren().add(this.createPrimaryContainer());
    }

    private Pane createPrimaryContainer() {
        VBox primaryContainer = new VBox();
        primaryContainer.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        primaryContainer.setMaxSize(PREF_WIDTH, PREF_HEIGHT);
        primaryContainer.setStyle("-fx-background-color: white; -fx-border-radius: 35px; -fx-background-radius: 35px");
        primaryContainer.setSpacing(20);

        primaryContainer.getChildren().addAll(
                this.createHeaderBox(),
                this.createSearchBarBox(),
                this.createLabelsScrollPane()
        );

        return primaryContainer;
    }

    private VBox createHeaderBox() {
        VBox headingBox = new VBox();
        headingBox.setAlignment(Pos.TOP_CENTER);
        headingBox.setPadding(new Insets(10));

        Label headingText = new Label("All my labels");
        headingText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        ImageView labelGif = new ImageView(new Image(getClass().getResourceAsStream("/gifs/label.gif")));
        labelGif.setFitHeight(100);
        labelGif.setFitWidth(100);

        headingBox.getChildren().addAll(
                labelGif,
                headingText
        );

        return headingBox;
    }

    private SearchBar createSearchBarBox() {
        SearchBar searchBar = new SearchBar();
        searchBar.setTextFieldPrompt("Search a label...");
        searchBar.setButtonAction(() -> {
            this.filterLabelsByName(searchBar.getBarText());
        });

        return searchBar;
    }

    private VBox createLabelsContainer() {
        labelsContainer = new VBox();
        labelsContainer.setFillWidth(true);
        labelsContainer.setStyle("-fx-background-color: white");

        for(LabelSummaryDTO personalLabel: labelManagementPC.getUserLabels())
            labelsContainer.getChildren().add(this.createRowInScrollPane(this.createBugBoardLabelRepresentation(personalLabel)));

        return labelsContainer;
    }

    private HBox createRowInScrollPane(BugBoardLabel labelToShow) {
        HBox rowInScrollPane = new HBox();
        rowInScrollPane.setPadding(new Insets(10));
        rowInScrollPane.setSpacing(10);
        rowInScrollPane.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        rowInScrollPane.getChildren().addAll(
                labelToShow,
                spacer,
                this.createManagementButton("/icons/edit.png", () -> clickModifyButton()),
                this.createManagementButton("/icons/trash.png", () -> clickDeleteButton())
        );

        return rowInScrollPane;
    }

    private void clickModifyButton() {

    }

    private void clickDeleteButton() {
        labelManagementPC.onDeleteButtonClicked();
    }

    private Button createManagementButton(String imageURL, Runnable actionToPerform) {
        Button managementButton = new Button();
        managementButton.setOnMouseClicked((mouseEvent) -> {
            actionToPerform.run();
        });

        ImageView attachedImage = new ImageView(new Image(getClass().getResourceAsStream(imageURL)));
        attachedImage.setFitWidth(30);
        attachedImage.setFitHeight(30);

        managementButton.setGraphic(attachedImage);

        return managementButton;
    }

    private BugBoardLabel createBugBoardLabelRepresentation(LabelSummaryDTO dtoToBugBoardLabel) {
        return new BugBoardLabel(dtoToBugBoardLabel);
    }

    private ScrollPane createLabelsScrollPane() {
        ScrollPane labelsScrollPane =  new ScrollPane(this.createLabelsContainer());
        labelsScrollPane.setPrefSize(300, 500);
        labelsScrollPane.setFitToWidth(true);
        labelsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return labelsScrollPane;
    }

    private void filterLabelsByName(String text) {
        labelsContainer.getChildren().clear();

        for(LabelSummaryDTO filteredLabel: labelManagementPC.getFilteredLabels(text))
            labelsContainer.getChildren().add(this.createRowInScrollPane(this.createBugBoardLabelRepresentation(filteredLabel)));

    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                close();
        });
    }

    public void close() {
        parentContainer.getChildren().remove(this);
    }

    public void showConfirmationDialog() {

    }
}
