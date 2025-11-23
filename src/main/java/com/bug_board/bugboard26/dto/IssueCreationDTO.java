package com.bug_board.bugboard26.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueCreationDTO {
    private String title;
    private String description;
    //private Tipology tipology;
    //private List<Byte[]> images;
    private Integer idProject;

    private Integer idLabel;

    private String username;
}
