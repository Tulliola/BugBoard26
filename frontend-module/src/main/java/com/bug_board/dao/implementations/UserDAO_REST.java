package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserDAO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class UserDAO_REST extends DAO_REST implements IUserDAO {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserDAO_REST(MyHTTPClient httpClient) {
        super(httpClient);
    }

    @Override
    public UserSummaryDTO registerNewUser(UserCreationDTO userToRegister)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException, BadConversionToJSONException {
        HttpRequest request;
        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/users/" + "register"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(userToRegister)
                            )
                    )
                    .build();
        } catch (IOException exc) {
            throw new BadConversionToJSONException("There has been an error in the conversion to DTO of the new user.");
        }

        return httpClient.sendAndHandle(request, UserSummaryDTO.class);
    }
}
