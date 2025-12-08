package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.AuthenticationController;
import com.bug_board.dto.LoginResponseDTO;
import com.bug_board.exceptions.architectural_controllers.AuthenticationException;
import com.bug_board.gui.views.LoginView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;

public class LoginPC {
    private final AuthenticationController authenticationController;
    private final INavigationManager navigationManager;
    private LoginView loginView;

    public LoginPC(AuthenticationController authenticationController, INavigationManager navigationManager) {
        this.authenticationController = authenticationController;
        this.navigationManager = navigationManager;
    }

    public void onLoginButtonClicked()
            throws AuthenticationException {
        if(this.loginView == null)
            throw new RuntimeException("Login view has not been set");

        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        LoginResponseDTO loginResponse = authenticationController.authenticate(username, password);

        SessionManager.getInstance().createSession(loginResponse.getToken(), loginResponse.getUsername(), loginResponse.getRole());

        navigationManager.closeWindow(loginView);
        navigationManager.navigateToHomePage();
    }

    public void setView(LoginView loginView) {
        this.loginView = loginView;
    }
}
