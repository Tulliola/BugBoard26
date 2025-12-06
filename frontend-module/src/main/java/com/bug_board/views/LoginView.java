package com.bug_board.views;

import com.bug_board.exceptions.architectural_controllers.InvalidCredentialsException;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.LoginPC;
import com.bug_board.utilities.*;
import com.bug_board.utilities.animations.FloatingLabelWithIcon;
import com.bug_board.utilities.animations.TextTypingEffect;
import com.bug_board.utilities.buttons.MyButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LoginView extends MyStage {
    private final LoginPC loginPC;

    private TextField usernameField = new TextField();
    private TextField passwordField = new PasswordField();
    private Label errorLabel = new Label();

    public LoginView(LoginPC loginPC) {
        this.loginPC = loginPC;

        this.initialize(this);
    }

    private void initialize(Stage stage) {
        Pane titleBar = this.createTitleBar();

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("background-gradient");
        Scene scene = new Scene(root, Color.TRANSPARENT);

        this.initializeStyleSheet("/css/components_style.css", scene);

        stage.setWidth(700);
        stage.setHeight(900);
        stage.setResizable(false);

        root.getChildren().addAll(
                titleBar,
                MySpacer.createVSpacer(),
                this.createLoginForm(),
                MySpacer.createVSpacer()
        );

        stage.setScene(scene);


    }

    private Pane createLoginForm() {
        VBox loginForm =  new VBox();
        loginForm.setId("login-form");
        loginForm.setAlignment(Pos.TOP_CENTER);

        errorLabel.setManaged(false);
        usernameField.requestFocus();

        loginForm.getChildren().addAll(
                MySpacer.createVSpacer(),
                this.createLogo(),
                MySpacer.createVSpacer(),
                this.createAnimatedText(),
                MySpacer.createVSpacer(),
                this.createTextFieldWithIcon(usernameField, "Username", "/icons/user_icon.png"),
                MySpacer.createVSpacer(),
                this.createTextFieldWithIcon(passwordField, "Password", "/icons/locker_icon.png"),
                MySpacer.createVSpacer(),
                this.createLoginButton(),
                MySpacer.createVSpacer(),
                errorLabel,
                MySpacer.createVSpacer(),
                this.createRegisterSuggestionPane(),
                MySpacer.createVSpacer()
        );

        return loginForm;
    }

    private TextFlow createAnimatedText() {
        Text preamble = new Text("It's not a " );
        Text bugText = new Text("bug, ");
        bugText.setId("animated-text");

        Text animatedText = new Text();
        animatedText.setId("animated-text");

        TextFlow textWrapper = new TextFlow(preamble, bugText, new Text("it's a "), animatedText);
        textWrapper.setTextAlignment(TextAlignment.CENTER);
        textWrapper.getStyleClass().add("motto-text");

        TextTypingEffect textTypingAnimation = new TextTypingEffect(
                animatedText,
                "new feature", "question", "documentation issue"
        );

        textTypingAnimation.startAnimation();

        return textWrapper;
    }

    private Pane createTextFieldWithIcon(TextField textField, String placeholder, String iconURL) {

        StackPane textFieldWrapper = FloatingLabelWithIcon.createFloatingLabelField(textField, placeholder);
        textField.setId("text-field-with-icon");

        ImageView icon = new ImageView(new Image(getClass().getResource(iconURL).toExternalForm()));
        icon.setFitHeight(30);
        icon.setFitWidth(30);
        icon.translateXProperty().set(5);
        icon.setPreserveRatio(true);
        icon.mouseTransparentProperty().set(true);

        textFieldWrapper.setAlignment(icon, Pos.CENTER_LEFT);
        textFieldWrapper.getChildren().add(icon);

        return textFieldWrapper;
    }

    private Pane createLogo() {
        VBox logoWrapper = new VBox();
        logoWrapper.setAlignment(Pos.TOP_CENTER);
        logoWrapper.setPadding(new Insets(10, 0, 0, 0));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logo_bugboard.png").toExternalForm()));
        logo.setFitHeight(175);
        logo.setFitWidth(175);
        logo.setPreserveRatio(true);
        logo.mouseTransparentProperty().set(true);

        logoWrapper.getChildren().add(logo);

        return logoWrapper;
    }

    private Button createLoginButton() {
        Button loginButton = new MyButton("LOGIN");
        loginButton.setPrefSize(450, 30);
        loginButton.setOnMouseClicked(event -> {
            this.clickLoginButton();
        });

        return loginButton;
    }

    private Pane createRegisterSuggestionPane() {
        VBox registerSuggestionPane = new VBox();
        registerSuggestionPane.setAlignment(Pos.CENTER);

        Label registerSuggestionLabel = new Label("Want to register your business? Get in touch!");
        registerSuggestionLabel.setAlignment(Pos.CENTER);
        registerSuggestionLabel.setTextAlignment(TextAlignment.CENTER);

        HBox contactUsPane = new HBox();
        contactUsPane.setAlignment(Pos.CENTER);
        contactUsPane.setSpacing(10);

        contactUsPane.getChildren().addAll(
                this.createSocialMediaButton("/icons/facebook-f.png", "facebook-btn"),
                this.createSocialMediaButton("/icons/google.png", "google-btn"),
                this.createSocialMediaButton("/icons/instagram.png", "instagram-btn")
        );

        registerSuggestionPane.getChildren().addAll(
                registerSuggestionLabel,
                MySpacer.createVSpacer(),
                contactUsPane
        );

        return registerSuggestionPane;
    }

    private Button createSocialMediaButton(String logoURL, String cssID) {
        ImageView logoSocialMedia = new ImageView(new Image(this.getClass().getResourceAsStream(logoURL)));
        logoSocialMedia.setFitHeight(20);
        logoSocialMedia.setFitWidth(20);
        logoSocialMedia.setPreserveRatio(true);

        Button socialMediaButton = new Button("", logoSocialMedia);
        socialMediaButton.getStyleClass().add("social-media-btn");
        socialMediaButton.setId(cssID);

        return socialMediaButton;
    }

    private void clickLoginButton() {
        try {
            errorLabel.setManaged(false);
            loginPC.onLoginButtonClicked(usernameField.getText(), passwordField.getText(), this);
        }
        catch (InvalidCredentialsException exc) {
            errorLabel.setText(exc.getMessage());
        }
        catch(BackendErrorException exc) {
            if(exc.getMessage().contains("401"))
                errorLabel.setText("Username or password not valid.");
        }
        catch(HTTPSendException | BadConversionToDTOException | BadConversionToJSONException throwables) {
            errorLabel.setText("Server is currently not responding.");
        }
        finally {

            errorLabel.setTextFill(Color.RED);
            errorLabel.setManaged(true);
        }
    }
}