package com.bug_board.dao.httphandler;

import com.bug_board.dto.ErrorResponseDTO;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHTTPClient {
    private final ObjectMapper mapper = new ObjectMapper();

    private HttpResponse<String> executeSend(HttpRequest request)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {

        HttpClient http = HttpClient.newHttpClient();
        HttpResponse<String> response;

        try {
            response = http.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException throwables) {
            throw new HTTPSendException("There has been an issue in the sending phase: " + throwables.getMessage());
        }

        if (response.statusCode() >= 200 && response.statusCode() < 300)
            return response;
        else {
            handleErrorResponse(response);
            return null;
        }
    }

    public <T> T sendAndHandle(HttpRequest request, TypeReference<T> responseType)
            throws ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException {

        HttpResponse<String> response = this.executeSend(request);

        try {
            System.out.println(response.body());
            return mapper.readValue(response.body(), responseType);
        }
        catch (IOException exc) {
            throw new BadConversionToDTOException("There has been an error in the process of conversion to DTO.\n" +
                    "STATUS CODE: " + response.statusCode());
        }
    }

    public <T> T sendAndHandle(HttpRequest request, Class<T> responseType)
            throws ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException {

        HttpResponse<String> response = this.executeSend(request);

        if(responseType == Void.class)
            return (T) response;

        try {
            return mapper.readValue(response.body(), responseType);
        }
        catch (IOException exc) {
            throw new BadConversionToDTOException("There has been an error in the process of conversion to DTO.\n" +
                    "STATUS CODE: " + response.statusCode());
        }
    }

    private void handleErrorResponse(HttpResponse<String> responseToHandle) throws ErrorHTTPResponseException, BadConversionToDTOException {
        try{
            ErrorResponseDTO errorDTO = mapper.readValue(responseToHandle.body(), ErrorResponseDTO.class);

            throw new ErrorHTTPResponseException("There has been an error from the server.\n" +
                    "STATUS CODE: " + errorDTO.getStatus() + "\n" +
                    "MESSAGE: " + errorDTO.getPhrase() + "\n" +
                    "ERROR: " + errorDTO.getError());
        }
        catch(IOException exc){
            throw new BadConversionToDTOException("There has been an error in the process of conversion to DTO.\n" +
                    "STATUS CODE: " + responseToHandle.statusCode());
        }
    }
}