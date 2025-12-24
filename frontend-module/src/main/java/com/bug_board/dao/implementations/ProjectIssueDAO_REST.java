package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IProjectIssueDAO;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.session_manager.SessionManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

public class ProjectIssueDAO_REST extends DAO_REST implements IProjectIssueDAO {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProjectIssueDAO_REST(MyHTTPClient httpClient) {
        super(httpClient);
    }

    @Override
    public List<IssueSummaryDTO> getAllProjectIssues(Integer idProject, IssueFiltersDTO filters)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException, BadConversionToJSONException {
        IssueFiltersDTO filtersToMap = (filters == null) ? new IssueFiltersDTO() : filters;

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/projects/" + idProject + "/issues/search"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(filtersToMap))
                    ).build();
        } catch (IOException exc) {
            throw new BadConversionToJSONException("There has been an error in the conversion to JSON of the issue DTO.");
        }

        TypeReference<List<IssueSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }

    @Override
    public IssueSummaryDTO createNewIssue(Integer idProject, IssueCreationDTO issueToCreate)
            throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;
        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/projects/" + idProject + "/issues"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer "+SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(issueToCreate)))
                    .build();
        } catch (IOException exc) {
            throw new BadConversionToJSONException("There has been an error in the conversion to JSON of the issueSummary DTO.");
        }

        return httpClient.sendAndHandle(request, IssueSummaryDTO.class);
    }

    @Override
    public List<IssueImageDTO> getAllIssueImages(Integer idProject, Integer idIssue)
            throws ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException {
        HttpRequest request;

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/projects/" + idProject + "/issues/" + idIssue + "/images"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<IssueImageDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}