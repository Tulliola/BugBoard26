package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IProjectDAO;
import com.bug_board.dto.CollaboratorAssociationDTO;
import com.bug_board.dto.UserSummaryDTO;
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

public class ProjectDAO_REST extends DAO_REST implements IProjectDAO {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProjectDAO_REST(MyHTTPClient httpClient) {
        super(httpClient);
    }

    @Override
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject, CollaboratorAssociationDTO collaborator)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException, BadConversionToJSONException {
        HttpRequest request;

        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/projects/" + idProject + "/users/collaborators"))
                    .header("Authorization", "Bearer "+ SessionManager.getInstance().getJwtToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(collaborator)))
                    .build();
        } catch (IOException e) {
            throw new BadConversionToJSONException("There has been an error in the conversion to DTO of the collaborator.");
        }

        return httpClient.sendAndHandle(request, UserSummaryDTO.class);
    }

    @Override
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/projects/" + idProject +"/addable-users"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<UserSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}
