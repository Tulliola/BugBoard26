package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserDAO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;

public class UserDAO_REST implements IUserDAO {
    private final String baseUrl = "http://localhost:8080/api/users/";
    private final MyHTTPClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserDAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public UserSummaryDTO registerNewUser(UserCreationDTO userToRegister)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;
        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "register"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(userToRegister)
                            )
                    )
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return httpClient.sendAndHandle(request, UserSummaryDTO.class);
    }
}
