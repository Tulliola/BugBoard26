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
        searchBar.setSearchButtonAction(() -> {
            this.filterLabelsByName(searchBar.getBarText());
        });
        searchBar.setClearButtonAction(() -> {
            this.filterLabelsByName("");
        });

        return searchBar;
    }

    private VBox createLabelsContainer() {
        labelsContainer = new VBox();
        labelsContainer.setFillWidth(true);
        labelsContainer.setStyle("-fx-background-color: white; -fx-alignment: center;");

        for(LabelSummaryDTO personalLabel: labelManagementPC.getUserLabels())
            labelsContainer.getChildren().add(this.createRowInScrollPane(personalLabel));

        return labelsContainer;
    }

    private HBox createRowInScrollPane(LabelSummaryDTO labelToShow) {
        HBox rowInScrollPane = new HBox();
        rowInScrollPane.setPadding(new Insets(10));
        rowInScrollPane.setSpacing(10);
        rowInScrollPane.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        rowInScrollPane.getChildren().addAll(
                this.createBugBoardLabelRepresentation(labelToShow),
                spacer
        );

        if(labelToShow.getCreator() != null){
            rowInScrollPane.getChildren().addAll(
                this.createManagementButton("/icons/edit.png", () -> clickModifyButton(new BugBoardLabel(labelToShow))),
                this.createManagementButton("/icons/trash.png", () -> clickDeleteButton(new BugBoardLabel(labelToShow)))
            );
        }

        return rowInScrollPane;
    }

    private void clickModifyButton(BugBoardLabel labelToModify) {
        labelManagementPC.onModifyButtonClicked(labelToModify, this);
    }

    private void clickDeleteButton(BugBoardLabel labelToDelete) {
        labelManagementPC.onDeleteButtonClicked(labelToDelete, this);
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
        labelsScrollPane.setStyle("-fx-background-color: white");

        return labelsScrollPane;
    }

    private void filterLabelsByName(String text) {
        labelsContainer.getChildren().clear();

        for(LabelSummaryDTO filteredLabel: labelManagementPC.getFilteredLabels(text))
            labelsContainer.getChildren().add(this.createRowInScrollPane(filteredLabel));

        if(labelsContainer.getChildren().isEmpty())
            showNoLabelFoundPane();
    }

    private void showNoLabelFoundPane() {
        Label noLabelsFoundLabel = new Label("No labels found");
        noLabelsFoundLabel.setStyle("-fx-font-style: italic; -fx-font-size: 20px;");

        ImageView noLabelsFoundImage = new ImageView(new Image(getClass().getResourceAsStream("/icons/not_found.png")));

        labelsContainer.getChildren().addAll(noLabelsFoundImage,noLabelsFoundLabel);
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

    public void showConfirmationDialog(Pane confirmationDialog) {
        this.getChildren().add(confirmationDialog);
    }

    public void showOverlayedContent(Pane overlayContent){
        this.getChildren().add(overlayContent);
    }
}
