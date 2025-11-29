package com.bug_board.dao.implementations;

import com.bug_board.dao.interfaces.IAuthenticationDAO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class AuthenticationDAO_REST implements IAuthenticationDAO {
    private final String baseUrl = "http://localhost:8080/api/";

    @Override
    public String authenticate(UserAuthenticationDTO authenticationDTO) {
        ObjectMapper mapperToJson = new ObjectMapper();
        String jsonString = "";

        try{
            jsonString = mapperToJson.writeValueAsString(authenticationDTO);
        }
        catch(IOException exc){
            System.out.println(exc.getMessage());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "auth"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        jsonString
                )).build();
    }
}
