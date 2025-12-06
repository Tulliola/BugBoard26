package com.bug_board.utilities;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.animations.CardFlipEffect;
import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.implementations.ComponentButtonFactory;
import com.bug_board.utilities.buttons.factory.interfaces.ButtonFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.io.ByteArrayInputStream;

public class ProjectCard extends StackPane {
    final ProjectSummaryDTO projectToShow;
    private final StackPane cardPane;
    private final Pane frontCard;
    private final Pane backCard;
    private Button flipButton;
    private final CardFlipEffect cardAnimation;
    private Integer idProject;

    public ProjectCard(ProjectSummaryDTO projectSummaryDTO) {
        projectToShow = projectSummaryDTO;
        idProject = projectToShow.getIdProject();

        frontCard = createFrontCard();
        backCard = createBackCard();

        cardPane = new StackPane(backCard, frontCard);

        Pane buttonPane = this.setButtonFlipPane();
        StackPane.setAlignment(buttonPane, Pos.TOP_CENTER);
        this.getChildren().addAll(cardPane, buttonPane);

        cardAnimation = new CardFlipEffect(frontCard, backCard, cardPane);
    }

    /* Methods for front card */

    private Pane createFrontCard() {
        VBox front = new VBox();
        front.getStyleClass().add("project-card");

        front.getChildren().addAll(
                this.createFrontHeaderPane(),
                this.createLogoPane(),
                this.createFrontFooter()
        );

        return front;
    }

    private Pane createFrontHeaderPane() {
        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.TOP_CENTER);
        headerPane.setId("project-card-header");

        headerPane.getChildren().add(this.createProjectNamePane());

        return headerPane;
    }

    private Pane createProjectNamePane() {
        VBox projectCreatorPane = new VBox();
        projectCreatorPane.setPadding(new Insets(10, 10, 10, 10));
        projectCreatorPane.setAlignment(Pos.CENTER);

        Text projectName = new Text(projectToShow.getTitle());
        projectName.setStyle("-fx-font-size: 20px; -fx-fill: white");

        projectCreatorPane.getChildren().addAll(MySpacer.createVSpacer(), projectName, MySpacer.createVSpacer());

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

    private Pane createFrontFooter() {
        VBox footer = new VBox();
        footer.setAlignment(Pos.BOTTOM_CENTER);

        Text projectCreator = new Text("Created by " + projectToShow.getProjectCreator());
        projectCreator.setStyle("-fx-font-size: 15px; -fx-font-style: italic; -fx-fill: gray");
        projectCreator.setTextAlignment(TextAlignment.CENTER);
        VBox.setVgrow(footer, Priority.ALWAYS);

        ButtonFactory buttonFactory = ComponentButtonFactory.getProjectCardButtonsByRole(
                SessionManager.getInstance().getRole().getRoleName()
        );

        for(ButtonDefinition definition:  buttonFactory.createProjectCardButtons()) {
            Button buttonToAdd = new Button(definition.getText());

            buttonToAdd.setOnMouseClicked(event -> {
                buttonActionHandler(definition.getActionId());
            });

            footer.getChildren().add(buttonToAdd);
        }

        footer.getChildren().addAll(projectCreator);
        footer.setStyle("-fx-background-radius: 0 0 30px 30px; -fx-border-radius: 0 0 30px 30px; -fx-padding: 10px; -fx-spacing: 20");

        return footer;
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
        descriptionPane.setStyle("-fx-background-color: #273B7A");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(descriptionPane);
        scrollPane.setMaxHeight(500);

        descriptionPane.prefWidthProperty().bind(scrollPane.widthProperty());

        Text description = new Text(projectToShow.getDescription());
        description.setStyle("-fx-fill: white");
        description.setStyle("-fx-font-family: Poppins; " +
                "-fx-font-weight: normal; -fx-font-size: 13; " +
                "-fx-fill: white");
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
        flipImageView.setFitHeight(40);
        flipImageView.setFitWidth(40);

        flipButton = new Button();
        flipButton.setGraphic(flipImageView);
        flipButton.setStyle("-fx-background-color: transparent");
        flipButton.setId("flip-button");

        flipButton.setMouseTransparent(true);

        flipPane.setOnMouseEntered(event -> {
            flipPane.setCursor(Cursor.HAND);
        });

        flipPane.setOnMouseClicked(e -> {
            flipImageView.setImage(flipGif);
            cardAnimation.handleFlip();
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

    /* Methods for factory and action handlers */

    private void buttonActionHandler(String actionId) {
        switch (actionId) {
            case "VIEW_ISSUES":
                clickViewIssueButton();
            case "REPORT_ISSUE":
                clickReportIssueButton();
            default:
                break;
        }
    }

    private void clickReportIssueButton() {
    }

    private void clickViewIssueButton() {
    }

    private ProjectSummaryDTO getProject() {
        return this.projectToShow;
    }
}