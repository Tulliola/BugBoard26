package com.bug_board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueFiltersDTO {
    private List<String> issueStates;
    private List<String> issueTipologies;
    private List<String> issuePriorities;
}
