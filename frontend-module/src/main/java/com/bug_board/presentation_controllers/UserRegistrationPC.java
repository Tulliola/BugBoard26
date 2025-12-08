package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserRegistrationController;
import com.bug_board.dto.EmailToSendDTO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.enum_classes.UserRole;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.gui.panes.ConfirmTransactionPane;
import com.bug_board.gui.panes.UserRegistrationFormPane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

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
        UserCreationDTO userToCreate = new  UserCreationDTO();

        String emailToRegister = userRegistrationFormPane.getEmail();
        UserRole role = userRegistrationFormPane.getRole();

        userToCreate.setEmail(emailToRegister);
        userToCreate.setRole(role);

        this.userRegistrationController.registerUser(userToCreate);

        this.addConfirmationPane();

        this.showConfirmationPane();
    }


    private void addConfirmationPane() {
        userRegistrationFormPane.getChildren().removeLast();

        userRegistrationFormPane.getChildren().add(new ConfirmTransactionPane("/gifs/added_user.gif", "User created successfully!"));
    }

    private void showConfirmationPane(){
        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            userRegistrationFormPane.close();
        });

        delay.play();
    }
}
