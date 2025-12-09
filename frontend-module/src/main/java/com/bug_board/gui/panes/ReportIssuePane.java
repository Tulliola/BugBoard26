package com.bug_board.gui.panes;

import com.bug_board.presentation_controllers.HomePagePC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class ReportIssuePane extends StackPane {
    private StackPane parentContainer;
    private HomePagePC homePagePC;

    private VBox contentPane = new VBox();

    private HBox typeIssueBox = new HBox();
    private Text typeIssueText =  new Text("Which type of issue would you like to report?");

    private HBox typeIssueRadioButtonBox = new HBox();
    private ToggleGroup typeIssueToggleGroup = new ToggleGroup();
    private RadioButton bugRadioButton = new RadioButton("Bug");
    private RadioButton questionRadioButton = new RadioButton("Question");
    private RadioButton documentationRadioButton = new RadioButton("Documentation");
    private RadioButton newFeatureRadioButton = new RadioButton("New Feature");

    public ReportIssuePane(StackPane parentContainer, HomePagePC homePagePC) {
        this.parentContainer = parentContainer;
        this.homePagePC = homePagePC;

        this.initialize();
    }


    private void initialize() {
        this.setBackground();
        this.setStyle();
        this.getChildren().add(setContentPane());
    }

    private VBox setContentPane() {
        typeIssueText.setStyle("-fx-font-size: 20px");
        typeIssueText.setTextAlignment(TextAlignment.CENTER);
        typeIssueBox.getChildren().add(typeIssueText);
        typeIssueBox.setAlignment(Pos.CENTER);
        contentPane.getChildren().add(typeIssueBox);


        bugRadioButton.setToggleGroup(typeIssueToggleGroup);
        questionRadioButton.setToggleGroup(typeIssueToggleGroup);
        documentationRadioButton.setToggleGroup(typeIssueToggleGroup);
        newFeatureRadioButton.setToggleGroup(typeIssueToggleGroup);

        typeIssueRadioButtonBox.getChildren().add(bugRadioButton);
        typeIssueRadioButtonBox.getChildren().add(questionRadioButton);
        typeIssueRadioButtonBox.getChildren().add(documentationRadioButton);
        typeIssueRadioButtonBox.getChildren().add(newFeatureRadioButton);


        typeIssueRadioButtonBox.setAlignment(Pos.CENTER);
        typeIssueRadioButtonBox.setSpacing(15);
        typeIssueRadioButtonBox.setPadding(new Insets(20, 20, 20, 20));

        contentPane.getChildren().add(typeIssueRadioButtonBox);
        return contentPane;
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                close();
        });
    }

    private void setStyle() {
        this.getStyleClass().add("overlay-pane");
        this.setStyle("-fx-max-height: 750px; -fx-min-height: 750px; -fx-min-width: 1000px; -fx-max-width: 1000px");
    }

    public void close() {
        parentContainer.getChildren().remove(this);
    }
}
