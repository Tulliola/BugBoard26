package com.bug_board.utilities;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.utilities.animations.CardFlipEffect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.io.ByteArrayInputStream;

public class ProjectCard extends StackPane {
    final ProjectSummaryDTO projectToShow;
    private final StackPane cardPane;
    private final Pane frontCard;
    private final Pane backCard;
    private Button flipButton;

    public ProjectCard(ProjectSummaryDTO projectSummaryDTO) {
        projectToShow = projectSummaryDTO;

        frontCard = createFrontCard();
        backCard = createBackCard();

        cardPane = new StackPane(backCard, frontCard);

        Pane buttonPane = this.setButtonFlipPane();
        StackPane.setAlignment(buttonPane, Pos.TOP_CENTER);
        this.getChildren().addAll(cardPane, buttonPane);

        CardFlipEffect flipEffect = new CardFlipEffect(frontCard, backCard, cardPane);
        buttonPane.setOnMouseClicked(event -> {
            flipEffect.handleFlip();
        });
    }

    /* Methods for front card */

    private Pane createFrontCard() {
        VBox front = new VBox();
        front.getStyleClass().add("project-card");

        Button prova = new Button("Prova");
        prova.setOnMouseClicked(event -> {
            System.out.println("Funziona");
        });

        front.getChildren().addAll(
                this.createFrontHeaderPane(),
                this.createLogoPane(),
                prova
        );

        return front;
    }

    private Pane createFrontHeaderPane() {
        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.TOP_CENTER);
        headerPane.setId("project-card-header");

        headerPane.getChildren().add(this.createProjectCreatorPane());

        return headerPane;
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

    /* Methods for back card */

    private Pane createBackCard() {
        VBox back = new VBox();
        back.getStyleClass().add("project-card");
        back.setStyle("-fx-background-color: #2AC4AC");

        VBox footer = new VBox();
        footer.setId("project-card-footer");

        back.getChildren().addAll(
                this.createBackHeaderPane(),
                this.createDescriptionPane(),
                footer
        );

        return back;
    }

    private Pane createBackHeaderPane() {
        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.TOP_CENTER);
        headerPane.setId("project-card-header");

        return headerPane;
    }

    private ScrollPane createDescriptionPane() {
        VBox descriptionPane = new VBox();
        descriptionPane.setPadding(new Insets(10, 10, 10, 10));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(descriptionPane);
        scrollPane.setMaxHeight(500);

        descriptionPane.prefWidthProperty().bind(scrollPane.widthProperty());

        Text description = new Text(projectToShow.getDescription());
        description.setStyle("-fx-font-family: Poppins; -fx-font-weight: normal; -fx-font-size: 13");
        description.wrappingWidthProperty().bind(
                descriptionPane.widthProperty().subtract(descriptionPane.getPadding().getLeft() + descriptionPane.getPadding().getRight())
        );

        descriptionPane.getChildren().add(description);

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    private Pane setButtonFlipPane() {
        StackPane flipPane = new StackPane();
        flipPane.setStyle("-fx-border-radius: 2px; -fx-background-radius: 2px; -fx-border-color: #2AC4AC");
        flipPane.setMaxSize(60, 60);

        Circle circularBackground = new Circle(25);
        circularBackground.setFill(Color.WHITE);

        Image flipStatic = new Image(getClass().getResourceAsStream("/icons/flip_static.png"));
        Image flipGif = new Image(getClass().getResourceAsStream("/gifs/flip.gif"));

        ImageView flipImageView = new ImageView(flipStatic);
        flipImageView.setFitHeight(34);
        flipImageView.setFitWidth(34);

        flipButton = new Button();
        flipButton.setGraphic(flipImageView);
        flipButton.setStyle("-fx-background-color: transparent");
        flipButton.setEffect(null);

        flipButton.setMouseTransparent(true);

        flipPane.setOnMouseEntered(e -> {
            flipPane.setCursor(Cursor.HAND);
            flipImageView.setImage(flipGif);
        });

        flipPane.setOnMouseExited(e -> {
            flipPane.setCursor(Cursor.DEFAULT);
            flipImageView.setImage(flipStatic);
        });

        flipPane.getChildren().addAll(circularBackground,  flipButton);
        flipPane.setStyle("-fx-background-radius: 35px; -fx-border-radius: 35px");
        flipPane.translateYProperty().set(-30);

        flipPane.setViewOrder(-5.0);

        return flipPane;
    }

}