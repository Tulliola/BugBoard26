package com.bug_board.views;

import com.bug_board.exceptions.architectural_controllers.InvalidCredentialsException;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.LoginPC;
import com.bug_board.utilities.MySpacer;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.animations.TextTypingEffect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LoginView extends MyStage {
    private final LoginPC loginPC;

    private TextField usernameField;
    private PasswordField passwordField;

    public LoginView(LoginPC loginPC) {
        this.loginPC = loginPC;

        this.initialize(this);
    }

    public void initialize(Stage stage) {
        Pane titleBar = this.createTitleBar();

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, Color.WHITE);

        this.initializeStyleSheet("/css/components_style.css", scene);

        stage.setWidth(700);
        stage.setHeight(800);
        stage.setResizable(false);

        root.getChildren().addAll(titleBar, MySpacer.createVSpacer(), this.createLoginForm(), MySpacer.createVSpacer());

        stage.setScene(scene);
    }

    public Pane createLoginForm() {
        VBox loginForm =  new VBox();
        loginForm.setId("login-form");
        loginForm.setAlignment(Pos.CENTER);

        Text staticText = new Text("It's not a bug, it's a ");

        Text animatedText = new Text();
        animatedText.setId("animated-text");

        TextFlow textWrapper = new TextFlow(staticText, animatedText);
        textWrapper.setTextAlignment(TextAlignment.CENTER);

        TextTypingEffect textTypingAnimation = new TextTypingEffect(
                animatedText,
                "new feature", "question", "documentation issue"
        );

        loginForm.getChildren().addAll(
                MySpacer.createVSpacer(),
                textWrapper,
                new Label("Username"),
                new TextField(),
                new Label("Password"),
                new PasswordField(),
                MySpacer.createVSpacer()
        );

        textTypingAnimation.startAnimation();

        return loginForm;
    }

    public void clickLoginButton() {
        try {
            loginPC.onLoginButtonClicked(usernameField.getText(), passwordField.getText());
        }
        catch (InvalidCredentialsException | HTTPSendException | BadConversionToDTOException | BackendErrorException | BadConversionToJSONException exc) {
            System.out.println(exc.getMessage());
        }
    }
}