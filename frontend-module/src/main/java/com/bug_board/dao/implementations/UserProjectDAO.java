package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

public class UserProjectDAO implements IUserProjectDAO {
    private final String baseUrl = "http://localhost:8080/api/me/projects/";
    private final MyHTTPClient httpClient;

    public UserProjectDAO(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;
        URI uri;

        if(projectNameToFilter == null || projectNameToFilter.equals(""))
            uri = URI.create(baseUrl + "overviewed");
        else
            uri = URI.create(baseUrl+"overviewed?projectName="+projectNameToFilter);


        request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<ProjectSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        HttpRequest request;
        URI uri;
        if(projectNameToFilter == null || projectNameToFilter.equals(""))
            uri = URI.create(baseUrl + "working-on");
        else
            uri = URI.create(baseUrl + "working-on?projectName="+projectNameToFilter);

        request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<ProjectSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}
