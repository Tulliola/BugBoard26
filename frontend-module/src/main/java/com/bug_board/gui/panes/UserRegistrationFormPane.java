package com.bug_board.gui.panes;

import com.bug_board.utilities.MyRadioButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class UserRegistrationFormPane extends StackPane {
    private StackPane parentPane;
    private VBox contentPane = new VBox();
    private Text addCollaborator;
    private HBox userTypeChooser = new HBox();
    private TextField emailToRegister = new TextField();
    private Button confirmButton = new Button("Confirm registration");
    private MyRadioButton adminButton;
    private MyRadioButton userButton;
    private ToggleGroup userTypeChooserGroup = new ToggleGroup();
    private Text userDescription;
    private Text adminDescription;

    private VBox adminButtonBox = new VBox();
    private VBox userButtonBox = new VBox();

    private StackPane userDescriptionBox = new StackPane();
    private StackPane adminDescriptionBox = new StackPane();

    public  UserRegistrationFormPane(StackPane parentPane) {
        this.parentPane = parentPane;
        this.initialize();
    }

    private void initialize() {
        contentPane.setId("user-registration-form");
        this.setBackground();
        this.setContentPane();
        this.getChildren().add(contentPane);
    }

    private void setContentPane() {
        this.setGif();
        this.setAddCollaboratorText();
        this.setRadioButtonsBox();
        this.setEmailTextField();
        this.setConfirmButton();
    }

    private void setGif() {
        Image gif = new Image(getClass().getResourceAsStream("/gifs/painter.gif"));
        ImageView imageView = new ImageView(gif);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        contentPane.getChildren().add(imageView);
    }

    private void setConfirmButton() {
        this.confirmButton.setOnAction(e -> {

        });
        contentPane.getChildren().addAll(confirmButton);
    }

    private void setEmailTextField() {
        emailToRegister.setPromptText("New user's email address");
        contentPane.getChildren().add(emailToRegister);
    }

    private void setRadioButtonsBox() {
        this.setRadioButtons();
        this.setUserTypeChooserBox();

        userTypeChooser.setId("radio-group-container");
        userTypeChooser.setAlignment(Pos.CENTER);
        contentPane.getChildren().add(userTypeChooser);
    }

    private void setUserTypeChooserBox() {
        adminButtonBox.getChildren().add(adminButton);
        Text administrator = new Text("Administrator");
//        administrator.setStyle("-fx-fill: red;");
        adminButtonBox.getChildren().add(administrator);
        adminButtonBox.setAlignment(Pos.TOP_CENTER);
        adminButtonBox.setSpacing(15);

        userButtonBox.getChildren().add(userButton);
        Text user = new Text("Regular User");
//        user.setStyle("-fx-fill: red;");
        userButtonBox.getChildren().add(user);
        userButtonBox.setAlignment(Pos.TOP_CENTER);
        userButtonBox.setSpacing(15);

        adminButtonBox.getStyleClass().add("role-radio-button-box-width");
        userButtonBox.getStyleClass().add("role-radio-button-box-width");

        setDescriptionsStackPanes();

        userTypeChooser.getChildren().addAll(adminButtonBox, userButtonBox);
        userTypeChooser.setStyle("-fx-border-color: #2AC4AC; -fx-border-width: 2px 0 2px 0; -fx-padding: 5px");
    }

    private void setDescriptionsStackPanes() {
        userDescription = new Text("A regular user can:\n\u2022 Report an issue\n\u2022 Check his own issues (filter and order them)\n\u2022 Comment an issue\n\u2022 Update an own issue's state");
        adminDescription = new Text("An adminastrator can:\n\u2022 Create a new user\n\u2022 Assign a particular issue to a specific user\n\u2022 " +
                "Check an issue report dashboard\n\u2022 Receive monthly reports about projects' issues\n\u2022 Mark an issue as duplicated\n\u2022 Update all issues' states");

        userDescription.setStyle("-fx-font-size: 13px; -fx-font-weight: 200");
        adminDescription.setStyle("-fx-font-size: 13px; -fx-font-weight: 200");

        userDescriptionBox.getStyleClass().add("role-radio-button-box-width");
        adminDescriptionBox.getStyleClass().add("role-radio-button-box-width");

        userDescription.wrappingWidthProperty().bind(userDescriptionBox.widthProperty());
        adminDescription.wrappingWidthProperty().bind(adminDescriptionBox.widthProperty());

        userDescription.setManaged(false);
        adminDescription.setManaged(false);

        userDescriptionBox.setMaxHeight(0);
        adminDescriptionBox.setMaxHeight(0);

        adminDescriptionBox.setOpacity(0);
        userDescriptionBox.setOpacity(0);

        userDescriptionBox.getChildren().add(userDescription);
        adminDescriptionBox.getChildren().add(adminDescription);

        adminButtonBox.getChildren().add(adminDescriptionBox);
        userButtonBox.getChildren().add(userDescriptionBox);
    }

    private void animateDescription(StackPane container, boolean show){
        Duration duration = Duration.millis(500);

        double endOpacity;
        double endHeight;
        if(show){
            endOpacity = 1.0;
            endHeight = Double.MAX_VALUE;
        }
        else{
            endHeight = 0;
            endOpacity = 0.0;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(duration,
                        new KeyValue(
                                container.maxHeightProperty(), endHeight
                        )
                ),
                new KeyFrame(
                        duration,
                        new KeyValue(
                                container.opacityProperty(), endOpacity
                        )
                )
        );

        timeline.play();
    }

    private void setRadioButtons() {
        setRadioButtonsIcons();
        setRadioButtonsActions();

        userButton.setToggleGroup(userTypeChooserGroup);
        adminButton.setToggleGroup(userTypeChooserGroup);
    }

    private void setRadioButtonsActions() {
        userTypeChooserGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == adminButton) {
                adminDescription.setManaged(true);
                animateDescription(adminDescriptionBox, true);
                animateDescription(userDescriptionBox, false);
            }
            else if (newToggle == userButton) {
                userDescription.setManaged(true);
                animateDescription(userDescriptionBox, true);
                animateDescription(adminDescriptionBox, false);
            }
        });
    }


    private void setRadioButtonsIcons(){
        adminButton = new MyRadioButton(new Image(getClass().getResourceAsStream("/icons/adminIcon.png")));
        userButton = new MyRadioButton(new Image(getClass().getResourceAsStream("/icons/userIcon.png")));

        adminButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        userButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private void setAddCollaboratorText() {
        addCollaborator = new Text("Add new collaborator");
        addCollaborator.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        addCollaborator.setTextAlignment(TextAlignment.CENTER);
        contentPane.getChildren().add(addCollaborator);
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.4);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this) {
                parentPane.getChildren().remove(this);
            }
        });
    }
}