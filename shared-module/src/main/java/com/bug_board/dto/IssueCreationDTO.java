package com.bug_board.dto;

import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

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
    private List<byte[]> images;
    private final IssueState state = IssueState.TODO;

    private Integer idProject;

    private List<Integer> idLabels;
}
