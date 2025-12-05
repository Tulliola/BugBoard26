package com.bug_board.utilities;

import com.bug_board.dto.ProjectSummaryDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;

public class ProjectCard extends VBox {
    final ProjectSummaryDTO projectToShow;

    public ProjectCard(ProjectSummaryDTO projectSummaryDTO) {
        projectToShow = projectSummaryDTO;

        this.setProjectCardLookAndFeel();
    }

    private void setProjectCardLookAndFeel() {
        this.getStyleClass().add("project-card");

        this.getChildren().addAll(
                this.headerPane(),
                this.createLogoPane()
        );
    }

    private Pane headerPane() {
        StackPane wrapper = new StackPane();

        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.TOP_CENTER);
        headerPane.setStyle("" +
            "-fx-background-color: linear-gradient(" +
            "to top," +
            "#273B7A," +
            "#2AC4AC);" +
                "-fx-border-radius: 30px 30px 0 0; -fx-background-radius: 30px 30px 0 0");

        headerPane.getChildren().add(this.createProjectCreatorPane());
        wrapper.getChildren().addAll(headerPane,  this.createButtonFlipPane());

        return wrapper;
    }

    private Pane createButtonFlipPane() {
        StackPane buttonWrapper = new StackPane();
        buttonWrapper.setStyle("-fx-border-radius: 2px; -fx-background-radius: 2px; -fx-border-color: #2AC4AC");

        Circle circularBackground = new Circle(25);
        circularBackground.setFill(Color.WHITE);

        Image flipStatic = new Image(getClass().getResourceAsStream("/icons/flip_static.png"));
        Image flipGif = new Image(getClass().getResourceAsStream("/gifs/flip.gif"));

        ImageView flipImageView = new ImageView(flipStatic);
        flipImageView.setFitHeight(34);
        flipImageView.setFitWidth(34);

        Button flipButton = new Button();
        flipButton.setGraphic(flipImageView);
        flipButton.setStyle("-fx-background-color: transparent");
        flipButton.setEffect(null);

        flipButton.setOnMouseEntered(e -> {
            flipImageView.setImage(flipGif);
        });

        flipButton.setOnMouseExited(e -> {
            flipImageView.setImage(flipStatic);
        });

        buttonWrapper.getChildren().addAll(circularBackground,  flipButton);
        buttonWrapper.translateYProperty().set(-30);
        buttonWrapper.setStyle("-fx-background-radius: 35px; -fx-border-radius: 35px");

        return buttonWrapper;
    }

    private Pane createProjectCreatorPane() {
        VBox projectCreatorPane = new VBox();
        projectCreatorPane.setPadding(new Insets(10, 10, 10, 10));

        Text projectName = new Text(projectToShow.getTitle());
        projectName.setFill(Color.WHITE);

        Text projectCreator = new Text("Created by " + projectToShow.getProjectCreator());
        projectCreator.setStyle("-fx-font-family: Poppins; -fx-font-weight: normal; -fx-font-size: 13");
        projectCreator.setFill(Color.WHITE);

        projectCreatorPane.getChildren().addAll(projectName, projectCreator);

        return projectCreatorPane;
    }

    private Pane createLogoPane () {
        VBox logoPane = new VBox();
        logoPane.setPadding(new Insets(10, 10, 10, 10));
        logoPane.setAlignment(Pos.TOP_CENTER);

        Image imageToFit;
        if(projectToShow.getImage() == null)
            imageToFit = new Image(getClass().getResource("/images/logo_bugboard.png").toExternalForm());
        else
            imageToFit = new Image(new ByteArrayInputStream(projectToShow.getImage()));

        ImageView logoImage = new ImageView(imageToFit);
        logoImage.setFitWidth(175);
        logoImage.setFitHeight(175);

        logoPane.getChildren().add(logoImage);

        return logoPane;
    }
}