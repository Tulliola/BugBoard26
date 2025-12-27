package com.bug_board.dto.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class IEmailToSendDTO {
    @NotNull(message = "Addressee must not be null.")
    @NotBlank(message = "Addressee must be specified.")
    protected String addressee;

    @NotNull(message = "Username must not be null.")
    @NotBlank(message = "Username must be specified.")
    protected String username;

    @NotNull(message = "Password must not be null.")
    @NotBlank(message = "Password must be specified.")
    protected String password;
}
