package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserIssueDAO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_instance.SessionManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.List;

public class UserIssueDAO_REST implements IUserIssueDAO {
    private final String baseUrl = "http://localhost:8080/api/me/issues/";
    private final MyHTTPClient httpClient;

    public UserIssueDAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<IssueSummaryDTO> getPersonalIssues()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<IssueSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}
