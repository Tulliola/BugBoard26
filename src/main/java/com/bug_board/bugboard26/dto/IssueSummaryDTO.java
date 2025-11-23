package com.bug_board.bugboard26.dto;

import com.bug_board.bugboard26.enum_classes.IssueState;
import com.bug_board.bugboard26.enum_classes.IssueTipology;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class IssueSummaryDTO {
    private Integer idIssue;
    private String title;
    private String description;
    private IssueState state;
    private IssueTipology tipology;
    private List<Byte[]> images;
    private Date creationDate;
    private Date resolutionDate;
}
