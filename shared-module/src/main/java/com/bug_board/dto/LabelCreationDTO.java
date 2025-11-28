package com.bug_board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreationDTO {
    private String creator;
    private String name;
    private String description;
    private String color;
}
