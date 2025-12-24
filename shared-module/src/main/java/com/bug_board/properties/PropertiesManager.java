package com.bug_board.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static PropertiesManager instance;
    private static String dtoProperties = "dto.properties";
    private final Properties properties;

    private PropertiesManager() {
        properties = new Properties();
        try {
            InputStream input = PropertiesManager.class.getClassLoader().getResourceAsStream(dtoProperties);

            if(input != null)
                properties.load(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesManager getInstance() {
        if(instance == null)
            instance = new PropertiesManager();

        return instance;
    }

    public String getMockEmail(){
        String envValue = System.getenv("MOCK_EMAIL_ADDRESS");

        if(envValue != null && !envValue.isEmpty())
            return envValue;

        return properties.getProperty("mock.email.addressee");
    }

    public String getEmailDTOImplementation(){

        String envValue = System.getenv("EMAIL_IMPLEMENTATION_TYPE");

        if(envValue != null && !envValue.isEmpty())
            return envValue;

        return properties.getProperty("email.implementation.type");
    }
}
