package com.bug_board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryDTO {
    private Integer idProject;
    private String title;
    private String description;
    private byte[] image;

    private String projectCreator;
}
