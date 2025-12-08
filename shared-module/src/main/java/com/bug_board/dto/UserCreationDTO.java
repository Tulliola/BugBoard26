package com.bug_board.dto;

import com.bug_board.enum_classes.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDTO {
    private String  email;
    private UserRole role;
    private byte[] bioPic;
}
