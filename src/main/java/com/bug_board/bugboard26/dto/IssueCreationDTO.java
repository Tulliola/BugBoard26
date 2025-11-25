package com.bug_board.bugboard26.dto;

import com.bug_board.bugboard26.enum_classes.IssueTipology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueCreationDTO {
    private String title;
    private String description;
    private IssueTipology tipology;
    private List<byte[]> images;

    private Integer idProject;

    private List<Integer> idLabel;

    private String username;
}
