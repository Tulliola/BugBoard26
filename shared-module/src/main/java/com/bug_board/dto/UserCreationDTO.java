package com.bug_board.dto;

import com.bug_board.enum_classes.UserRole;
import jakarta.validation.constraints.Email;
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
public class UserCreationDTO {
    @NotNull(message = "Email must not be null.")
    @NotBlank(message = "Email must be specified.")
    private String  email;

    @NotNull(message = "Role must not be null.")
    private UserRole role;
    private byte[] bioPic;
}
