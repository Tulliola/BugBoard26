package com.bug_board.architectural_controllers;

import com.bug_board.dao.implementations.UserDAO_REST;
import com.bug_board.dao.interfaces.IUserDAO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class UserRegistrationController {
    private final IUserDAO userDAO;

    public UserRegistrationController(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(UserCreationDTO userCreationDTO) throws UserRegistrationException {
        try {
            userDAO.registerNewUser(userCreationDTO);
        }
        catch(ErrorHTTPResponseException ex){
            if(ex.getMessage().contains("400"))
                throw new UserRegistrationException("User with specified email and role already exists.");
            else
                throw new UserRegistrationException(ex.getMessage());
        }
        catch (HTTPSendException | BadConversionToDTOException | BadConversionToJSONException throwables) {
            throw new UserRegistrationException("Couldn't register user: " + throwables.getMessage());
        }
    }
}
