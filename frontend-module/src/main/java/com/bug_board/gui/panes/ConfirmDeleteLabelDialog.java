package com.bug_board.gui.panes;

import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.utilities.BugBoardLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ConfirmDeleteLabelDialog extends StackPane {
    private final BugBoardLabel labelToEventuallyDelete;
    private final LabelManagementPC labelManagementPC;
    private final StackPane parentContainer;

    private static final int PREF_WIDTH = 500;
    private static final int PREF_HEIGHT = 225;

    public ConfirmDeleteLabelDialog(BugBoardLabel labelToEventuallyDelete,
                                    LabelManagementPC labelManagementPC,
                                    StackPane parentContainer) {
        this.parentContainer = parentContainer;
        this.labelToEventuallyDelete = labelToEventuallyDelete;
        this.labelManagementPC = labelManagementPC;

        initialize();
    }

    private void initialize() {
        setBackground();

        this.getChildren().add(this.createPrimaryContainer());
    }

    private VBox createPrimaryContainer() {
        VBox primaryContainer = new VBox();

        setDimensionsAndStyling(primaryContainer);
        setHeaderBox(primaryContainer);
        setBugBoardLabelContainer(primaryContainer);
        setConfirmationFooterBox(primaryContainer);

        return primaryContainer;
    }

    private void setDimensionsAndStyling(VBox primaryContainer) {
        primaryContainer.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        primaryContainer.setMaxSize(PREF_WIDTH, PREF_HEIGHT);
        primaryContainer.setStyle("-fx-background-radius: 35px; -fx-border-radius: 35px; -fx-background-color: white");
        primaryContainer.setSpacing(20);
    }

    private void setHeaderBox(VBox primaryContainer) {
        VBox headerBox = new VBox();
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.TOP_CENTER);

        Label headerText = new Label("Are you sure to delete the following label?");
        headerText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        headerBox.getChildren().addAll(
                headerText
        );

        primaryContainer.getChildren().add(headerBox);
    }

    private void setBugBoardLabelContainer(VBox primaryContainer) {
        VBox containerBox = new VBox();
        containerBox.setAlignment(Pos.CENTER);

        containerBox.getChildren().add(labelToEventuallyDelete);

        primaryContainer.getChildren().add(containerBox);
    }

    private void setConfirmationFooterBox(VBox primaryContainer) {
        VBox footerBox = new VBox();
        footerBox.setAlignment(Pos.TOP_CENTER);
        footerBox.setSpacing(10);

        Label warningText = new Label("This operation cannot be undone.");
        warningText.setStyle("-fx-font-size: 15px");

        Button confirmButton = new Button();
        confirmButton.setOnMouseClicked((mouseEvent) ->  {
            this.labelManagementPC.onConfirmDeleteButtonClicked(labelToEventuallyDelete.getLabelId());
        });

        ImageView attachedImage = new ImageView(new Image(getClass().getResourceAsStream("/icons/trash.png")));
        attachedImage.setFitWidth(30);
        attachedImage.setFitHeight(30);

        confirmButton.setGraphic(attachedImage);

        footerBox.getChildren().addAll(
                warningText,
                confirmButton
        );

        primaryContainer.getChildren().add(footerBox);
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
}
