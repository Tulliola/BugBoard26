package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.EmailToSendDTO;
import com.bug_board.dto.email.IEmailToSendDTO;

public interface IEmailService {
    public void sendEmail(IEmailToSendDTO email);
}
