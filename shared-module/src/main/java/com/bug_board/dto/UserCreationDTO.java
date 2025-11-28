package com.bug_board.dto;

import com.bug_board.enum_classes.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {
    private String  email;
    private String  password;
    private UserRole role;
    private byte[] bioPic;
}
