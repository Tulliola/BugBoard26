package com.bug_board.dto.email;

import com.bug_board.properties.PropertiesManager;


public class MockEmailToSendDTO extends IEmailToSendDTO {
    @Override
    public void setAddressee(String addressee) {
        this.addressee = PropertiesManager.getInstance().getMockEmail();
    }
}
