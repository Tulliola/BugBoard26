package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.TokenJWTDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.architectural_controllers.InvalidCredentialsException;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class AuthenticationController {
    private final IAuthenticationDAO authenticationDAO;

    public AuthenticationController(IAuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    public TokenJWTDTO authenticate(String username, String password)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException, BadConversionToJSONException, InvalidCredentialsException {
        if(username == null || username.isEmpty() ||  password == null || password.isEmpty())
            throw new InvalidCredentialsException("Invalid credentials.");

        UserAuthenticationDTO  userAuthenticationDTO = new UserAuthenticationDTO();

        userAuthenticationDTO.setUsername(username);
        userAuthenticationDTO.setPassword(password);

        return authenticationDAO.authenticate(userAuthenticationDTO);
    }
}
