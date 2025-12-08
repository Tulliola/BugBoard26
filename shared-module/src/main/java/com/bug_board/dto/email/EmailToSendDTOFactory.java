package com.bug_board.dto.email;

import com.bug_board.dto.EmailToSendDTO;
import com.bug_board.properties.PropertiesManager;

public class EmailToSendDTOFactory {
    private static EmailToSendDTOFactory instance;

    private EmailToSendDTOFactory() {}

    public static EmailToSendDTOFactory getInstance() {
        if (instance == null) {
            instance = new EmailToSendDTOFactory();
        }

        return instance;
    }

    public IEmailToSendDTO getEmailDTO(){
        if(PropertiesManager.getInstance().getEmailDTOImplementation().equalsIgnoreCase("mock")) {
            return new MockEmailToSendDTO();
        }
        else
            return new EmailToSendDTO();
    }
}
