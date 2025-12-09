package com.bug_board.utilities;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssueTipology;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    }

    private VBox createIssueTypeBox() {
        VBox issueTypeBox = new VBox();
        issueTypeBox.setAlignment(Pos.CENTER);
        issueTypeBox.setStyle("-fx-background-color: -color-primary");
        issueTypeBox.getChildren().add(this.getIssueTypeImageView());

        return issueTypeBox;
    }

    private VBox createIssueSummaryBox() {
        VBox issueSummaryBox = new VBox();
        issueSummaryBox.setPadding(new Insets(10, 10, 10, 10));

        issueSummaryBox.getChildren().addAll(
                this.createCreatorSummaryPane(),
                this.createStateAndNamePane(),
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

    private HBox createCreatorSummaryPane() {
        HBox creatorSummaryPane = new HBox();
        creatorSummaryPane.setAlignment(Pos.CENTER_LEFT);

        Pane creatorBioPicPane = new Pane();
        creatorBioPicPane.setStyle("-fx-background-radius: 35px; -fx-border-radius: 35px");

        ImageView bioPic = new ImageView(new Image(new ByteArrayInputStream(issueToShow.getCreatorBioPic())));
        bioPic.setFitHeight(50);
        bioPic.setFitWidth(50);
        bioPic.setPreserveRatio(true);

        Label creatorLabel = new Label("Created by " + issueToShow.getCreatorName());
        creatorLabel.setPadding(new Insets(10, 10, 10, 10));

        creatorSummaryPane.getChildren().addAll(
                bioPic,
                creatorLabel
        );

        return creatorSummaryPane;
    }


    private HBox createStateAndNamePane() {
        HBox stateAndNamePane = new HBox();
        stateAndNamePane.setAlignment(Pos.CENTER_LEFT);
        stateAndNamePane.setPadding(new Insets(0, 0, 0, 10));

        Circle stateCircle = new Circle();
        stateCircle.setRadius(15);
        stateCircle.setFill(Color.web(issueToShow.getState().getColor()));

        Label nameLabel = new Label(issueToShow.getTitle());
        nameLabel.setPadding(new Insets(10, 10, 10, 10));
        stateAndNamePane.getChildren().addAll(
                stateCircle,
                nameLabel
        );

        return stateAndNamePane;
    }

    private HBox createDescriptionPane() {
        HBox descriptionPane = new HBox();
        descriptionPane.setAlignment(Pos.CENTER_LEFT);

        Label descriptionLabel = new Label(issueToShow.getDescription());
        descriptionLabel.setPadding(new Insets(10, 10, 10, 10));

        descriptionPane.getChildren().addAll(
                descriptionLabel
        );

        return descriptionPane;
    }


    private HBox createLabelsPane() {
        HBox labelsPane = new HBox();
        labelsPane.setPadding(new Insets(10, 10, 10, 10));
        labelsPane.setAlignment(Pos.CENTER_LEFT);

        for(LabelSummaryDTO label: this.issueToShow.getLabels())
            labelsPane.getChildren().add(new BugBoardLabel(label.getName(), label.getColor()));

        return labelsPane;
    }


    private HBox createCreationAndResolutionDatePane() {
        HBox datePane = new HBox();
        datePane.setAlignment(Pos.CENTER_LEFT);
        datePane.setPadding(new Insets(10, 10, 10, 10));

        Label creationDateLabel = new Label("Created on " + getDateFormatted(this.issueToShow.getCreationDate()));
        datePane.getChildren().add(creationDateLabel);

        if(this.issueToShow.getResolutionDate() != null)
            creationDateLabel.setText(creationDateLabel.getText() + " - " + getDateFormatted(this.issueToShow.getResolutionDate()));

        return datePane;
    }

    private String getDateFormatted(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "MM-dd-yyyy");

        return sdf.format(dateToFormat);
    }
}
