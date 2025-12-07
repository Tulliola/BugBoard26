package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.interfaces.IUserLabelDAO;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
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

public class UserLabelDAO_REST implements IUserLabelDAO {
    private final String baseUrl = "http://localhost:8080/api/me/labels/";
    private final MyHTTPClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserLabelDAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LabelSummaryDTO createNewLabel(LabelCreationDTO labelToCreate)
            throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;

        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(labelToCreate)
                    ))
                    .build();
        } catch (IOException e) {
            throw new BadConversionToJSONException("There has been an error in the conversion to DTO of LabelCreationDTO.");
        }

        return httpClient.sendAndHandle(request, LabelSummaryDTO.class);
    }

    @Override
    public void deleteLabel(Integer idLabel)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + idLabel))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .DELETE()
                .build();
        httpClient.sendAndHandle(request, Void.class);
    }

    @Override
    public LabelSummaryDTO modifyLabel(LabelModifyingDTO labelToModify) throws
            BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;

        try{
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + labelToModify.getIdLabel()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(labelToModify)))
                    .build();
        } catch (IOException e) {
            throw new BadConversionToJSONException("There has been an error in the conversion to DTO of LabelModifyingDTO.");
        }

        return httpClient.sendAndHandle(request, LabelSummaryDTO.class);
    }

    @Override
    public List<LabelSummaryDTO> getLabels()
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        HttpRequest request;

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getJwtToken())
                .GET()
                .build();

        TypeReference<List<LabelSummaryDTO>> typeRef = new TypeReference<>() {};

        return httpClient.sendAndHandle(request, typeRef);
    }
}
