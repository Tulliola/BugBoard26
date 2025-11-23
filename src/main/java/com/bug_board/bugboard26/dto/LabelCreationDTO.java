package com.bug_board.bugboard26.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class LabelCreationDTO {
    private String name;
    private String description;
    private String color;
}
