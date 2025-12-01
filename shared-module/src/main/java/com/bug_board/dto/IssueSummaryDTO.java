package com.bug_board.dto;

import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueSummaryDTO {
    private Integer idIssue;
    private String title;
    private String description;
    private IssueState state;
    private IssueTipology tipology;
    private List<byte[]> images;
    private Date creationDate;
    private Date resolutionDate;
}
