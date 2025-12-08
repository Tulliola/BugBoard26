package com.bug_board.utilities;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.enum_classes.IssueTipology;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

public class IssueSummaryCard extends HBox {
    private final IssueSummaryDTO issueToShow;

    public IssueSummaryCard(IssueSummaryDTO issueToShow) {
        this.issueToShow = issueToShow;

        initialize();
    }

    private void initialize() {
        this.getStyleClass().add("issue-summary-card");
    }

    private VBox createIssueTypeBox() {
        VBox issueTypeBox = new VBox();
        issueTypeBox.setAlignment(Pos.CENTER);
        issueTypeBox.getChildren().add(this.getIssueTypeImage());

        return issueTypeBox;
    }

    private ImageView getIssueTypeImage() {
        IssueTipology tipology = issueToShow.getTipology();
        return new ImageView(new Image(new ByteArrayInputStream(tipology.getAssociatedImage())));
    }
}
