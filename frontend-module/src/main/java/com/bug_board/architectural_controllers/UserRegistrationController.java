package com.bug_board.architectural_controllers;

import com.bug_board.dao.implementations.EmailSenderDAO_REST;
import com.bug_board.dao.implementations.UserDAO_REST;
import com.bug_board.dto.EmailToSendDTO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

public class UserRegistrationController {
    UserDAO_REST userDAO;
    EmailSenderDAO_REST emailSenderDAO;
    public UserRegistrationController(UserDAO_REST userDAO,  EmailSenderDAO_REST emailSenderDAO) {
        this.userDAO = userDAO;
        this.emailSenderDAO = emailSenderDAO;
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
