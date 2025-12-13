package com.bug_board.properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    private static PropertiesManager instance;
    private static String dtoProperties = "dto.properties";

    private PropertiesManager() {}

    public static PropertiesManager getInstance() {
        if(instance == null)
            instance = new PropertiesManager();

        return instance;
    }

    public String getMockEmail(){
        Properties properties = new Properties();
        try {
            properties.load(PropertiesManager.class.getClassLoader().getResourceAsStream(dtoProperties));
        }
        catch (IOException e) {
            throw new PropertiesNotFoundException("Couldn't retrieve any property you have specified.");
        }

        return properties.getProperty("app.mock-email-addressee");
    }

    public String getEmailDTOImplementation(){
        Properties properties = new Properties();

        try{
            properties.load(PropertiesManager.class.getClassLoader().getResourceAsStream(dtoProperties));
        }
        catch (IOException exc) {
            throw new RuntimeException("Couldn't retrieve any property you have specified.");
        }

        return properties.getProperty("app.email-dto-implementation");
    }
}
