package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.TokenJWTDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class AuthenticationDAO_REST implements IAuthenticationDAO {
    private final String baseUrl = "http://localhost:8080/api/";
    private final MyHTTPClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationDAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public TokenJWTDTO authenticate(UserAuthenticationDTO authenticationDTO)
            throws BadConversionToJSONException, BadConversionToDTOException, HTTPSendException, BackendErrorException {
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "auth"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(authenticationDTO)
                    )).build();
        }
        catch(IOException exc){
            throw new BadConversionToJSONException("There has been an error in the conversion to DTO of the authentication DTO.");
        }

        return httpClient.sendAndHandle(request, TokenJWTDTO.class);
    }

}
