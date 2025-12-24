package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;

public abstract class DAO_REST {
    protected final MyHTTPClient httpClient;
    protected final String baseUrl = "http://localhost:8080/api";

    public DAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }
}
