package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.email.IEmailToSendDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IEmailService {
    public void sendWelcomeEmail(@NotNull @Valid IEmailToSendDTO email);
}
