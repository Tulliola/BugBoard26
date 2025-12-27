package com.bug_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationDTO {
    @NotNull(message = "Username must not be null.")
    @NotBlank(message = "Username must be specified.")
    private String username;

    @NotNull(message = "Password must not be null.")
    @NotBlank(message = "Password must be specified.")
    private String password;
}
