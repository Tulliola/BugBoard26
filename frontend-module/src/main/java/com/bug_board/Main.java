package com.bug_board;

import com.bug_board.architectural_controllers.AuthenticationController;
import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.AuthenticationDAO_REST;
import com.bug_board.navigation_manager.implementations.NavigationManager_JavaFX;
import com.bug_board.presentation_controllers.LoginPC;
import com.bug_board.gui.views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        AuthenticationController authenticationController = new AuthenticationController(new AuthenticationDAO_REST(new MyHTTPClient()));
        LoginPC loginPC = new LoginPC(authenticationController, new NavigationManager_JavaFX());
        LoginView loginView = new LoginView(loginPC);

        loginView.show();

    }
}
