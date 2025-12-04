package com.bug_board.utilities;

import com.bug_board.dto.ProjectSummaryDTO;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjectCard extends VBox {
    final ProjectSummaryDTO projectToShow;

    public ProjectCard(ProjectSummaryDTO projectSummaryDTO) {
        projectToShow = projectSummaryDTO;
    }

    private void setProjectCardLookAndFeel() {
    }

    private Pane headerPane() {
        HBox headerPane = new HBox();
        headerPane.getStyleClass().add("project-card");

        headerPane.getChildren().add(this.createProjectCreatorPane());

        return headerPane;
    }

    private Pane createProjectCreatorPane() {
        VBox projectCreatorPane = new VBox();
        projectCreatorPane.setPadding(new Insets(10, 10, 10, 10));

        Text projectName = new Text(projectToShow.getTitle());

        Text projectCreator = new Text("Project created by " + projectToShow.getProjectCreator());
        projectCreator.setStyle("-fx-font-family: Poppins; -fx-font-weight: normal");

        projectCreatorPane.getChildren().addAll(projectName, projectCreator);

        return projectCreatorPane;
    }
}