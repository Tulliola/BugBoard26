package com.bug_board.architectural_controllers;

import com.bug_board.dao.implementations.UserDAO_REST;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class UserRegistrationController {
    UserDAO_REST userDAO;

    public UserRegistrationController(UserDAO_REST userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(UserCreationDTO userCreationDTO) throws UserRegistrationException {
        try {
            userDAO.registerNewUser(userCreationDTO);
        } catch (HTTPSendException | BadConversionToDTOException | BadConversionToJSONException | ErrorHTTPResponseException e) {
            throw new UserRegistrationException("Couldn't register user");
        }
    }
}
