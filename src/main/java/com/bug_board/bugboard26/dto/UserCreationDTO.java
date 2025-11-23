package com.bug_board.bugboard26.dto;

import com.bug_board.bugboard26.enum_classes.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {
    private String  email;
    private String  password;
    private String username;
    private UserRole role;
    private byte[] bioPic;
}
