package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.EmailToSendDTO;

public interface IEmailService {
    public void sendEmail(EmailToSendDTO email);
}
