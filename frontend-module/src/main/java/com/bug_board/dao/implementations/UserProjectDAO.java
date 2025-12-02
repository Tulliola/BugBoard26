package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_instance.SessionManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.List;

public class UserProjectDAO implements IUserProjectDAO {
    private final String baseUrl = "http://localhost:8080/api/me/projects/";
    private final MyHTTPClient httpClient;

    public UserProjectDAO(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameFilter) throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "overviewed"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<ProjectSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "working-on"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<ProjectSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}
