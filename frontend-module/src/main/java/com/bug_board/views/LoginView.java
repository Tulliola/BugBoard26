package com.bug_board.views;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.AuthenticationDAO_REST;
import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.architectural_controllers.InvalidCredentialsException;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.LoginPC;
import com.bug_board.utilities.MyStage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        Scene scene = new Scene(root, Color.WHITE);

        try {
            scene.getStylesheets()
                    .add(getClass()
                    .getResource("/css/components_style.css")
                    .toExternalForm());
        }
        catch(NullPointerException npe) {
            System.out.println(npe.getMessage());
        }

        stage.setWidth(700);
        stage.setHeight(420);
        stage.setResizable(false);

        usernameField = new TextField();
        passwordField = new PasswordField();

        Button button = new Button("Test HTTP Request");
        button.setOnAction((event -> {
            this.clickLoginButton();
        }));

        root.getChildren().addAll(titleBar);//, new Region(), usernameField, passwordField, button);

        stage.setScene(scene);
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