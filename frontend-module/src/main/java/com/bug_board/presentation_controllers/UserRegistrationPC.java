package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserRegistrationController;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.enum_classes.UserRole;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.gui.panes.UserRegistrationFormPane;
import com.bug_board.utilities.PasswordGenerator;

public class UserRegistrationPC {
    UserRegistrationFormPane userRegistrationFormPane;
    UserRegistrationController userRegistrationController;

    public UserRegistrationPC(UserRegistrationController userRegistrationController) {
        this.userRegistrationController = userRegistrationController;
    }


    public void setPane(UserRegistrationFormPane userRegistrationFormPane) {
        this.userRegistrationFormPane = userRegistrationFormPane;
    }

    public void onRegisterUserButtonClicked() throws UserRegistrationException {
        String emailToRegister = userRegistrationFormPane.getEmail();
        UserRole role = userRegistrationFormPane.getRole();

        UserCreationDTO userToCreate = new UserCreationDTO();
        userToCreate.setEmail(emailToRegister);
        userToCreate.setRole(role);
        String password = PasswordGenerator.generatePassword(16);
        System.out.println(password);
        userToCreate.setPassword(password);

        this.userRegistrationController.registerUser(userToCreate);
    }
}
