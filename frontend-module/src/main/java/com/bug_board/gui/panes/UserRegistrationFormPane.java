package com.bug_board.gui.panes;

import com.bug_board.enum_classes.UserRole;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.exceptions.views.InvalidEmailException;
import com.bug_board.exceptions.views.NoEmailSpecifiedException;
import com.bug_board.exceptions.views.NoRoleSpecifiedException;
import com.bug_board.presentation_controllers.UserRegistrationPC;
import com.bug_board.utilities.MyRadioButton;
import com.bug_board.utilities.animations.RoleRadioButtonAnimation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;


public class UserRegistrationFormPane extends StackPane {
    private final StackPane parentPane;

    private final UserRegistrationPC userRegistrationPC;

    private VBox contentPane = new VBox();

    private Text addCollaboratorHeader;

    private HBox userRoleChooserBox = new HBox();
    private VBox adminButtonBox = new VBox();
    private VBox userButtonBox = new VBox();
    private ToggleGroup roleRadioButtonGroup = new ToggleGroup();
    private MyRadioButton adminRadioButton;
    private MyRadioButton userRadioButton;
    private StackPane userDescriptionBox = new StackPane();
    private StackPane adminDescriptionBox = new StackPane();
    private Text userDescription;
    private Text adminDescription;

    private TextField emailToRegister = new TextField();

    private Label errorLabel = new Label();

    private Button confirmButton = new Button("Confirm registration");

    public  UserRegistrationFormPane(StackPane parentPane, UserRegistrationPC userRegistrationPC) {
        this.userRegistrationPC = userRegistrationPC;
        this.parentPane = parentPane;
        this.initialize();
    }

    private void initialize() {
        this.setBackground();

        this.setContentPane();

        this.getChildren().add(contentPane);
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this) {
                parentPane.getChildren().remove(this);
            }
        });
    }

    private void setContentPane() {
        contentPane.getStyleClass().add("overlay-pane");

        contentPane.getChildren().add(this.setGif());

        contentPane.getChildren().add(this.setAddCollaboratorText());

        contentPane.getChildren().add(this.setRadioButtonsBox());

        contentPane.getChildren().add(this.setEmailTextField());

        contentPane.getChildren().add(this.setErrorLabel());

        contentPane.getChildren().add(this.setConfirmButton());
    }

    private ImageView setGif() {
        Image gif = new Image(getClass().getResourceAsStream("/gifs/team_management.gif"));

        ImageView imageView = new ImageView(gif);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        return imageView;
    }

    private Text setAddCollaboratorText() {
        addCollaboratorHeader = new Text("Add new collaborator");
        addCollaboratorHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        addCollaboratorHeader.setTextAlignment(TextAlignment.CENTER);

        return addCollaboratorHeader;
    }

    private Pane setRadioButtonsBox() {
        this.setRadioButtons();

        userRoleChooserBox.setId("radio-group-container");
        userRoleChooserBox.setAlignment(Pos.CENTER);
        userRoleChooserBox.setStyle("-fx-border-color: #2AC4AC; -fx-border-width: 2px 0 2px 0; -fx-padding: 5px");

        userRoleChooserBox.getChildren().addAll(this.setUserTypeChoosersBoxes());

        return userRoleChooserBox;
    }

    private void setRadioButtons() {
        this.setRadioButtonsIcons();

        this.setRadioButtonsActions();

        userRadioButton.setToggleGroup(roleRadioButtonGroup);
        adminRadioButton.setToggleGroup(roleRadioButtonGroup);

        userRadioButton.getStyleClass().add("icon-radio");
        adminRadioButton.getStyleClass().add("icon-radio");
    }

    private void setRadioButtonsIcons(){
        adminRadioButton = new MyRadioButton(new Image(getClass().getResourceAsStream("/icons/adminIcon.png")));
        userRadioButton = new MyRadioButton(new Image(getClass().getResourceAsStream("/icons/userIcon.png")));

        adminRadioButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        userRadioButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private void setRadioButtonsActions() {
        roleRadioButtonGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == adminRadioButton) {
                adminDescription.setManaged(true);
                RoleRadioButtonAnimation.animate(adminDescriptionBox, true);
                RoleRadioButtonAnimation.animate(userDescriptionBox, false);
            }
            else if (newToggle == userRadioButton) {
                userDescription.setManaged(true);
                RoleRadioButtonAnimation.animate(userDescriptionBox, true);
                RoleRadioButtonAnimation.animate(adminDescriptionBox, false);
            }
        });
    }

    private List<VBox> setUserTypeChoosersBoxes() {
        adminButtonBox = setButtonBox(adminButtonBox, adminRadioButton, new Text("Administrator"));


        userButtonBox = setButtonBox(userButtonBox, userRadioButton, new Text("User"));

        this.setDescriptionsStackPanes();

        return List.of(adminButtonBox, userButtonBox);
    }

    private VBox setButtonBox(VBox buttonContainer, RadioButton button, Text textUnderButton){
        buttonContainer.getChildren().add(button);
        buttonContainer.getChildren().addAll(textUnderButton);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        buttonContainer.setSpacing(15);

        buttonContainer.getStyleClass().add("role-radio-button-box-width");

        return buttonContainer;
    }

    private TextField setEmailTextField() {
        emailToRegister.setPromptText("New user's email address");

        return emailToRegister;
    }

    private void setDescriptionsStackPanes() {
        this.setDescriptionTexts();

        userDescriptionBox.getStyleClass().add("role-radio-button-box-width");
        adminDescriptionBox.getStyleClass().add("role-radio-button-box-width");

        userDescriptionBox.setMaxHeight(0);
        adminDescriptionBox.setMaxHeight(0);

        adminDescriptionBox.setOpacity(0);
        userDescriptionBox.setOpacity(0);

        userDescriptionBox.getChildren().add(userDescription);
        adminDescriptionBox.getChildren().add(adminDescription);

        adminButtonBox.getChildren().add(adminDescriptionBox);
        userButtonBox.getChildren().add(userDescriptionBox);
    }

    private void setDescriptionTexts(){
        userDescription = new Text("A regular user can:\n\u2022 Report an issue\n\u2022 Check his own issues (filter and order them)\n\u2022 Comment an issue\n\u2022 Update an own issue's state");
        adminDescription = new Text("An adminastrator can:\n\u2022 Create a new user\n\u2022 Assign a particular issue to a specific user\n\u2022 " +
                "Check an issue report dashboard\n\u2022 Receive monthly reports about projects' issues\n\u2022 Mark an issue as duplicated\n\u2022 Update all issues' states");

        userDescription.setStyle("-fx-font-size: 13px; -fx-font-weight: 200;");
        adminDescription.setStyle("-fx-font-size: 13px; -fx-font-weight: 200;");

        userDescription.setTextAlignment(TextAlignment.CENTER);
        adminDescription.setTextAlignment(TextAlignment.CENTER);

        userDescription.wrappingWidthProperty().bind(userDescriptionBox.widthProperty());
        adminDescription.wrappingWidthProperty().bind(adminDescriptionBox.widthProperty());

        userDescription.setManaged(false);
        adminDescription.setManaged(false);
    }

    private Label setErrorLabel(){
        this.errorLabel.setStyle("-fx-text-fill: red");
        this.errorLabel.setManaged(false);
        this.errorLabel.setVisible(false);
        return this.errorLabel;
    }

    private Button setConfirmButton() {
        this.confirmButton.setOnAction(e -> {
            this.onConfirmButtonClicked();
        });
        return confirmButton;
    }

    private void onConfirmButtonClicked(){
        this.errorLabel.setManaged(false);
        this.errorLabel.setVisible(false);
        try {
            this.checkInputValidity();
            this.userRegistrationPC.onRegisterUserButtonClicked();
        }
        catch(NoRoleSpecifiedException | NoEmailSpecifiedException | InvalidEmailException | UserRegistrationException ex) {
            errorLabel.setText(ex.getMessage());
            errorLabel.setManaged(true);
            errorLabel.setVisible(true);
        }
    }

    private void checkInputValidity(){
        if(roleRadioButtonGroup.getSelectedToggle() == null)
            throw new NoRoleSpecifiedException("The role must be specified");

        if(emailToRegister.getText().isEmpty())
            throw new NoEmailSpecifiedException("The email address must be specified");

        if(!emailToRegister.getText().matches("^[a-zA-Z0-9._%+-]+@softengunina\\.[a-zA-Z]{2,3}$"))
            throw new InvalidEmailException("The email address is not valid");

    }

    public UserRole getRole() {
        if(adminRadioButton.isSelected())
            return UserRole.ROLE_ADMIN;
        else if(userRadioButton.isSelected())
            return UserRole.ROLE_USER;
        else
            throw new NoRoleSpecifiedException("The role must be specified");
    }

    public String getEmail(){
        return emailToRegister.getText();
    }

    public void close() {
        parentPane.getChildren().remove(this);
    }
}