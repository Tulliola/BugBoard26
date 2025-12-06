package com.bug_board.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationFormPane extends StackPane {
    private StackPane parentPane;
    private VBox contentPane = new VBox();
    private Text addCollaborator;
    private HBox userTypeChooser = new HBox();
    private TextField emailToRegister = new TextField();
    private Button confirmButton = new Button("Confirm registration");
    private ToggleGroup userTypeChooserGroup = new ToggleGroup();
    private List<RadioButton> userTypeButtons =  new ArrayList<>(2);


    public  UserRegistrationFormPane(StackPane parentPane) {
        this.parentPane = parentPane;

        this.initialize();
    }

    private void initialize() {
        contentPane.setId("user-registration-form");
        this.setBackground();
        this.setContentPane();
        this.getChildren().add(contentPane);
    }

    private void setContentPane() {
        this.setAddCollaboratorText();
        this.setRadioButtonsBox();
        this.setEmailTectField();
        this.setConfirmButton();
    }

    private void setConfirmButton() {
        this.confirmButton.setOnAction(e -> {

        });
        contentPane.getChildren().addAll(confirmButton);
    }

    private void setEmailTectField() {
        emailToRegister.setPromptText("New user's email address");
        contentPane.getChildren().add(emailToRegister);
    }

    private void setRadioButtonsBox() {
        this.setRadioButtons();
        userTypeChooser.getChildren().addAll(userTypeButtons);
        userTypeChooser.setAlignment(Pos.CENTER);
        contentPane.getChildren().addAll(userTypeChooser);
    }

    private void setRadioButtons() {
        RadioButton radioButton = new RadioButton("RegularUser");
        RadioButton radioButton2 = new RadioButton("Admin");
        radioButton.setToggleGroup(userTypeChooserGroup);
        radioButton2.setToggleGroup(userTypeChooserGroup);
        userTypeButtons.add(radioButton);
        userTypeButtons.add(radioButton2);
    }

    private void setAddCollaboratorText() {
        addCollaborator = new Text("Add new collaborator");
        addCollaborator.setTextAlignment(TextAlignment.CENTER);
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.4);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this) {
                parentPane.getChildren().remove(this);
            }
        });
    }
}
