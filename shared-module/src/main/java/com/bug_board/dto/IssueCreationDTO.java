package com.bug_board.dto;

import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreationDTO {
    private String title;
    private String description;
    private IssueTipology tipology;
    private IssuePriority priority;
    private IssueState state = IssueState.Todo;
    private List<byte[]> images;

    private Integer idProject;

    private List<Integer> idLabels;
}
