package com.bug_board.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class IEmailToSendDTO {
    protected String addressee;
    protected String username;
    protected String password;
}
