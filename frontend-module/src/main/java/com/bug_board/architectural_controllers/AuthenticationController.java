package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.LoginResponseDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.architectural_controllers.AuthenticationException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class AuthenticationController {
    private final IAuthenticationDAO authenticationDAO;

    public AuthenticationController(IAuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    public LoginResponseDTO authenticate(String username, String password)
            throws AuthenticationException {

        UserAuthenticationDTO  userAuthenticationDTO = new UserAuthenticationDTO();

        userAuthenticationDTO.setUsername(username);
        userAuthenticationDTO.setPassword(password);

        try {
            return authenticationDAO.authenticate(userAuthenticationDTO);
        }
        catch (ErrorHTTPResponseException exc) {
            if(exc.getMessage().contains("401"))
                throw new  AuthenticationException("Invalid username or password.");
            else
                throw new  AuthenticationException(exc.getMessage());
        }
        catch (BadConversionToJSONException | BadConversionToDTOException | HTTPSendException throwables) {
            throw new AuthenticationException("Server not responding.");
        }
    }
}
