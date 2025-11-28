package com.bug_board.dto;

import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueCreationDTO {
    private String title;
    private String description;
    private IssueTipology tipology;
    private IssueState state = IssueState.Todo;
    private List<byte[]> images;

    private Integer idProject;

    private List<Integer> idLabels;
}
