package com.bug_board.gui.panes;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.utilities.BugBoardLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.util.List;

public class IssueSummaryPane extends StackPane {

    private final List<byte[]> associatedImages;
    private final IssueSummaryDTO issueToShow;
    private final StackPane parentContainer;

    private VBox issueSummaryForm;

    public IssueSummaryPane(StackPane parentContainer,
                            IssueSummaryDTO issueToShow,
                            List<byte[]> associatedImages) {
        this.parentContainer = parentContainer;
        this.issueToShow = issueToShow;
        this.associatedImages = associatedImages;

        this.initalize();
    }

    private void initalize() {
        setBackground();
        setIssueSummaryForm();
    }

    private void setIssueSummaryForm() {
        issueSummaryForm = new VBox();
        issueSummaryForm.setAlignment(Pos.TOP_CENTER);
        issueSummaryForm.setId("form");

        setHeader();
        setDescriptionScrollPane();
        setLabelsBox();

        this.getChildren().add(issueSummaryForm);
    }

    private void setHeader() {
        VBox header = new VBox();
        header.setPadding(new Insets(10, 10, 10, 10));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);

        Label titleText = new Label(issueToShow.getTitle());
        titleText.setStyle("-fx-font-size: 20px");
        titleText.setWrapText(true);

        header.getChildren().addAll(
                createIssueTipologyStateAndPriorityBox(),
                titleText
        );

        issueSummaryForm.getChildren().add(header);
    }

    private StackPane createIssueTipologyStateAndPriorityBox() {
        StackPane issueTipologyAndPriorityWrapper = new StackPane();

        HBox issueTipologyPane = new HBox();
        issueTipologyPane.setStyle("-fx-background-color: -color-primary; -fx-background-radius: 35px; -fx-border-radius: 35px");
        issueTipologyPane.setMaxSize(120, 120);
        issueTipologyPane.setPadding(new Insets(10, 10, 10, 10));
        issueTipologyPane.setAlignment(Pos.CENTER);

        ImageView issueTipologyImageView = new ImageView(new Image(new ByteArrayInputStream(issueToShow.getTipology().getAssociatedImage())));
        issueTipologyImageView.setFitWidth(70);
        issueTipologyImageView.setFitHeight(70);

        issueTipologyPane.getChildren().add(issueTipologyImageView);
        issueTipologyAndPriorityWrapper.getChildren().add(issueTipologyPane);
        StackPane.setAlignment(issueTipologyPane, Pos.CENTER);

        this.setPriorityAndStateBox(issueTipologyAndPriorityWrapper);

        return issueTipologyAndPriorityWrapper;
    }

    private void setPriorityAndStateBox(StackPane issueTipologyAndPriorityWrapper) {
        Circle stateCircle = new Circle();
        stateCircle.setRadius(15);
        stateCircle.setFill(Color.web(issueToShow.getState().getColor()));
        Tooltip issueStateToolTip = new Tooltip(issueToShow.getState().toString());
        issueStateToolTip.setStyle("-fx-text-fill: " + issueToShow.getState().getColor());
        issueStateToolTip.setShowDelay(Duration.millis(100));
        Tooltip.install(stateCircle, issueStateToolTip);

        issueTipologyAndPriorityWrapper.getChildren().add(stateCircle);
        StackPane.setAlignment(stateCircle, Pos.TOP_RIGHT);

        if(issueToShow.getPriority().getAssociatedImage() != null) {
            ImageView issuePriorityImageView = new ImageView(new Image(new ByteArrayInputStream(issueToShow.getPriority().getAssociatedImage())));
            issuePriorityImageView.setFitHeight(40);
            issuePriorityImageView.setFitWidth(40);

            issueTipologyAndPriorityWrapper.getChildren().add(issuePriorityImageView);
            StackPane.setAlignment(issuePriorityImageView, Pos.TOP_LEFT);
        }

    }

    private void setDescriptionScrollPane() {
        VBox descriptionBox = new VBox(5);
        descriptionBox.setStyle("-fx-background-color: transparent");

        TextArea descriptionText = new TextArea(issueToShow.getDescription());
        descriptionText.setWrapText(true);
        descriptionText.getStyleClass().add("no-border-textarea");
        descriptionText.setStyle("-fx-border-color: none none #A3A3A3 none; -fx-border-width: 0 0 1 0; -fx-font-weight: normal");
        descriptionText.setEditable(false);
        descriptionText.setPrefHeight(200);
        descriptionText.setMaxHeight(200);

        Label labelDescription = new Label("Description");
        labelDescription.setStyle("-fx-font-weight: bold");

        descriptionBox.getChildren().addAll(
                labelDescription,
                descriptionText
        );

        issueSummaryForm.getChildren().add(descriptionBox);
    }

    private void setLabelsBox() {
        FlowPane labelsBox = new FlowPane();
        labelsBox.setPadding(new Insets(10, 10, 10, 10));

        if(issueToShow.getLabels() != null && !issueToShow.getLabels().isEmpty())
            for(LabelSummaryDTO labelToShow: issueToShow.getLabels()){
                BugBoardLabel bugBoardLabel = new BugBoardLabel(labelToShow.getName(), labelToShow.getColor());
                bugBoardLabel.setToolTipDescription(labelToShow.getDescription());

                labelsBox.getChildren().add(bugBoardLabel);
            }
        else{
            Label noLabelAssociatedText = new Label("No labels associated to this issue");
            noLabelAssociatedText.setStyle("-fx-font-style: italic");
            labelsBox.getChildren().add(noLabelAssociatedText);
        }

        issueSummaryForm.getChildren().add(labelsBox);
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
