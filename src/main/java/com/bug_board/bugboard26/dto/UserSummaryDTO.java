package com.bug_board.bugboard26.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryDTO {
    private String username;
    private byte[] image;
}
