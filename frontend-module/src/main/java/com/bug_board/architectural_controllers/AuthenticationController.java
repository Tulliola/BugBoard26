package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.LoginResponseDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class AuthenticationController {
    private final IAuthenticationDAO authenticationDAO;

    public AuthenticationController(IAuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    public LoginResponseDTO authenticate(String username, String password)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException, BadConversionToJSONException {

        UserAuthenticationDTO  userAuthenticationDTO = new UserAuthenticationDTO();

        userAuthenticationDTO.setUsername(username);
        userAuthenticationDTO.setPassword(password);

        return authenticationDAO.authenticate(userAuthenticationDTO);
    }
}
