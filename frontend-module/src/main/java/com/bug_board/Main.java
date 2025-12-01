package com.bug_board;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.AuthenticationDAO_REST;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        Scene scene = new Scene(root, Color.WHITE);

        stage.setTitle("Login page!");
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setResizable(false);

        TextField username = new TextField();
        TextField password = new TextField();

        Button button = new Button("Test HTTP request");
        button.setOnAction(event -> {
            AuthenticationDAO_REST testDAO = new AuthenticationDAO_REST(new MyHTTPClient());
            UserAuthenticationDTO dto = new UserAuthenticationDTO();

            dto.setUsername(username.getText());
            dto.setPassword(password.getText());

            try {
                System.out.println(testDAO.authenticate(dto).getToken());
            }
            catch (BadConversionToJSONException | BadConversionToDTOException | HTTPSendException | BackendErrorException throwables) {
                System.out.println(throwables.getMessage());
            }

        });

        root.getChildren().addAll(username,  password, button);

        stage.setScene(scene);
        stage.show();
    }
}
