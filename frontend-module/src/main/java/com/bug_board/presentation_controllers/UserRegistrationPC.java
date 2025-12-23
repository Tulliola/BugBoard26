package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserRegistrationController;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.enum_classes.UserRole;
import com.bug_board.exceptions.architectural_controllers.UserRegistrationException;
import com.bug_board.gui.panes.TransactionPane;
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

        try {
            this.userRegistrationController.registerUser(userToCreate);
            this.addConfirmationPane();
        }
        catch (UserRegistrationException e) {
            if(e.getMessage().contains("exists"))
                this.addUserAlreadyExists();
            else
                this.addErrorPane();
        }
        finally {
            this.showConfirmationPane();
        }
    }

    private void addUserAlreadyExists() {
        userRegistrationFormPane.getChildren().removeLast();

        TransactionPane transactionPane = new TransactionPane("/gifs/error_adding_user.gif", "User already exists");
        transactionPane.setErrorGradient();

        userRegistrationFormPane.getChildren().add(transactionPane);
    }

    private void addErrorPane() {
        userRegistrationFormPane.getChildren().removeLast();

        TransactionPane transactionPane = new TransactionPane("/gifs/generic_error.gif", "Couldn't register the user. Please, try later.");
        transactionPane.setErrorGradient();

        userRegistrationFormPane.getChildren().add(transactionPane);
    }

    private void addConfirmationPane() {
        userRegistrationFormPane.getChildren().removeLast();

        userRegistrationFormPane.getChildren().add(new TransactionPane("/gifs/successful_transaction.gif", "User created successfully!"));
    }

    private void showConfirmationPane(){
        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            userRegistrationFormPane.close();
        });

        delay.play();
    }
}
