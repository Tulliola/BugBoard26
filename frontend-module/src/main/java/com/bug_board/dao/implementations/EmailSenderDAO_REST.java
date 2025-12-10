package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IEmailSenderDAO;
import com.bug_board.dto.email.EmailToSendDTO;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class EmailSenderDAO_REST implements IEmailSenderDAO {
    private final String baseUrl = "http://localhost:8080/api";
    private final MyHTTPClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailSenderDAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void send(EmailToSendDTO emailDTO)
            throws BadConversionToJSONException, ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException {
        HttpRequest request;

        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/" + "mail"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(emailDTO)
                    ))
                    .build();
        } catch (IOException e) {
            throw new BadConversionToJSONException("There has been an error in the conversion of the mailDTO");
        }

        httpClient.sendAndHandle(request, Void.class);
    }
}
