package com.bug_board.bugboard26.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSummaryDTO {
    private Integer idProject;
    private String title;
    private String description;
    private byte[] image;

    private String projectCreator;
}
