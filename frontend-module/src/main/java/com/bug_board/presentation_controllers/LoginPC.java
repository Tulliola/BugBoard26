package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.AuthenticationController;
import com.bug_board.dto.TokenJWTDTO;
import com.bug_board.exceptions.architectural_controllers.InvalidCredentialsException;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;

public class LoginPC {
    private final AuthenticationController authenticationController;
    private final INavigationManager navigationManager;

    public LoginPC(AuthenticationController authenticationController, INavigationManager navigationManager) {
        this.authenticationController = authenticationController;
        this.navigationManager = navigationManager;
    }

    public void onLoginButtonClicked(String username, String password)
            throws InvalidCredentialsException, HTTPSendException, BadConversionToDTOException, BackendErrorException, BadConversionToJSONException {
        TokenJWTDTO jwtToken = authenticationController.authenticate(username, password);

        SessionManager.getInstance().createSession(jwtToken.getToken());

        navigationManager.navigateToHomePageFromLogin();
    }
}
