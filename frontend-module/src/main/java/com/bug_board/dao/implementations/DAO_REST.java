package com.bug_board.dao.implementations;

import com.bug_board.dao.httphandler.MyHTTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class DAO_REST {
    protected final MyHTTPClient httpClient;
    protected static final String BASE_URL;

    static {
        String urlTrovato = null;
        File configFile = new File("server.properties");

        if (configFile.exists()) {
            try (FileInputStream input = new FileInputStream(configFile)) {
                Properties prop = new Properties();
                prop.load(input);
                urlTrovato = prop.getProperty("server.url");
            }
            catch (IOException ex) {
                throw new RuntimeException("Error: couldn't load configuration file.");
            }
        }
        else
            throw new RuntimeException("Couldn't find the file you have specified.");

        if (urlTrovato == null || urlTrovato.isEmpty())
            urlTrovato = "http://localhost:8080/api";

        BASE_URL = urlTrovato;
    }

    public DAO_REST(MyHTTPClient httpClient) {
        this.httpClient = httpClient;
    }
}
