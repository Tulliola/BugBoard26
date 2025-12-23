package com.bug_board.utilities;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.utilities.animations.OnMouseEnteredHoverEffect;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueSummaryCard extends HBox {
    private final IssueSummaryDTO issueToShow;

    public IssueSummaryCard(IssueSummaryDTO issueToShow) {
        this.issueToShow = issueToShow;

        initialize();
    }

    private void initialize() {
        this.getStyleClass().add("issue-summary-card");

        this.getChildren().addAll(
                this.createIssueSummaryBox(),
                this.createIssueTypeBox()
        );

        OnMouseEnteredHoverEffect.addHoverEffect(this);
        this.setMaxWidth(Region.USE_PREF_SIZE);
    }

    private VBox createIssueTypeBox() {
        VBox issueTypeBox = new VBox();
        issueTypeBox.setAlignment(Pos.CENTER);
        issueTypeBox.setStyle("-fx-background-color: -color-primary");
        issueTypeBox.setPrefSize(200, 200);
        issueTypeBox.getChildren().add(this.getIssueTypeImageView());

        return issueTypeBox;
    }

    private VBox createIssueSummaryBox() {
        VBox issueSummaryBox = new VBox();
        issueSummaryBox.setPadding(new Insets(10, 10, 10, 10));
        issueSummaryBox.setPrefWidth(850);
        issueSummaryBox.setMaxWidth(850);

        issueSummaryBox.getChildren().addAll(
                this.createStateAndCreatorSummaryPane(),
                this.createNamePane(),
                this.createLabelsPane(),
                this.createDescriptionPane(),
                this.createCreationAndResolutionDatePane()
        );

        return issueSummaryBox;
    }

    private ImageView getIssueTypeImageView() {
        IssueTipology tipology = issueToShow.getTipology();
        ImageView image = new ImageView(new Image(new ByteArrayInputStream(tipology.getAssociatedImage())));
        image.setFitWidth(100);
        image.setFitHeight(100);

        return image;
    }

    private HBox createStateAndCreatorSummaryPane() {
        HBox creatorSummaryPane = new HBox();
        creatorSummaryPane.setAlignment(Pos.CENTER_LEFT);
        creatorSummaryPane.setSpacing(5);

        ImageView bioPic = new ImageView(new Image(new ByteArrayInputStream(issueToShow.getCreatorBioPic())));
        bioPic.setFitHeight(50);
        bioPic.setFitWidth(50);
        bioPic.setPreserveRatio(true);

        Label creatorLabel = new Label("Created by " + issueToShow.getCreatorName());
        creatorLabel.setPadding(new Insets(10, 10, 10, 10));
        creatorLabel.getStyleClass().add("issue-summary-card-label");

        Circle stateCircle = new Circle();
        stateCircle.setRadius(15);
        stateCircle.setFill(Color.web(issueToShow.getState().getColor()));
        Tooltip issueStateToolTip = new Tooltip(issueToShow.getState().toString());
        issueStateToolTip.setStyle("-fx-text-fill: " + issueToShow.getState().getColor());
        issueStateToolTip.setShowDelay(Duration.millis(100));
        Tooltip.install(stateCircle, issueStateToolTip);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        creatorSummaryPane.getChildren().addAll(
                bioPic,
                creatorLabel,
                spacer,
                stateCircle
        );

        if(issueToShow.getPriority().getAssociatedImage() != null) {
            ImageView issuePriorityImageView = new ImageView(new Image(new ByteArrayInputStream(issueToShow.getPriority().getAssociatedImage())));
            issuePriorityImageView.setFitWidth(35);
            issuePriorityImageView.setFitHeight(35);
            Tooltip issuePriorityToolTip = new Tooltip(issueToShow.getPriority().toString());
            issuePriorityToolTip.setShowDelay(Duration.millis(100));
            Tooltip.install(issuePriorityImageView, issuePriorityToolTip);

            creatorSummaryPane.getChildren().add(3, issuePriorityImageView);
        }

        return creatorSummaryPane;
    }


    private HBox createNamePane() {
        HBox stateAndNamePane = new HBox();
        stateAndNamePane.setAlignment(Pos.CENTER_LEFT);
        stateAndNamePane.setPadding(new Insets(0, 0, 0, 10));

        Label nameLabel = new Label(issueToShow.getTitle());
        nameLabel.setPadding(new Insets(10, 10, 10, 10));
        nameLabel.getStyleClass().add("issue-summary-card-label");

        stateAndNamePane.getChildren().addAll(
                nameLabel
        );

        return stateAndNamePane;
    }

    private HBox createDescriptionPane() {
        HBox descriptionPane = new HBox();
        descriptionPane.setAlignment(Pos.CENTER_LEFT);

        Label descriptionLabel = new Label(issueToShow.getDescription());
        descriptionLabel.setPadding(new Insets(10, 10, 10, 10));
        descriptionLabel.getStyleClass().add("issue-summary-card-label");

        descriptionPane.getChildren().addAll(
                descriptionLabel
        );

        return descriptionPane;
    }


    private FlowPane createLabelsPane() {
        FlowPane labelsPane = new FlowPane();

        labelsPane.setPadding(new Insets(10, 10, 10, 10));
        labelsPane.setAlignment(Pos.CENTER);
        labelsPane.setHgap(10);
        labelsPane.setPrefWrapLength(800);

        if(issueToShow.getLabels().isEmpty()) {
            BugBoardLabel noLabelsLabel = new BugBoardLabel("No labels associated", "#A3A3A3");
            noLabelsLabel.setScaleX(0.75);
            noLabelsLabel.setScaleY(0.75);

            Group labelGroups = new Group(noLabelsLabel);

            labelsPane.getChildren().add(labelGroups);
        }

        for(LabelSummaryDTO label: this.issueToShow.getLabels()) {
            BugBoardLabel bugBoardLabel = new BugBoardLabel(label);
            bugBoardLabel.setScaleX(0.75);
            bugBoardLabel.setScaleY(0.75);

            Group labelContainer = new Group(bugBoardLabel);


            labelsPane.getChildren().add(labelContainer);
        }

        return labelsPane;
    }


    private HBox createCreationAndResolutionDatePane() {
        HBox datePane = new HBox();
        datePane.setAlignment(Pos.CENTER_LEFT);
        datePane.setPadding(new Insets(10, 10, 10, 10));

        Label creationDateLabel = new Label("Created on " + getDateFormatted(this.issueToShow.getCreationDate()));
        datePane.getChildren().add(creationDateLabel);

        if(this.issueToShow.getResolutionDate() != null)
            creationDateLabel.setText(creationDateLabel.getText() + " - Solved on " + getDateFormatted(this.issueToShow.getResolutionDate()));

        return datePane;
    }

    private String getDateFormatted(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "MM-dd-yyyy");

        return sdf.format(dateToFormat);
    }
}
