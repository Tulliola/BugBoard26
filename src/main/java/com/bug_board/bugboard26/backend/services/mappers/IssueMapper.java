package com.bug_board.bugboard26.backend.services.mappers;

import com.bug_board.bugboard26.backend.entity.Issue;
import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;

public class IssueMapper {
    public static IssueSummaryDTO toIssueSummaryDTO(Issue issueToMap) {
        IssueSummaryDTO mappedIssue = new IssueSummaryDTO();

        mappedIssue.setIdIssue(issueToMap.getIdIssue());
        mappedIssue.setTitle(issueToMap.getTitle());
        mappedIssue.setDescription(issueToMap.getDescription());
        mappedIssue.setState(issueToMap.getState());
        mappedIssue.setTipology(issueToMap.getTipology());
        mappedIssue.setImages(issueToMap.getImages());
        mappedIssue.setCreationDate(issueToMap.getCreationDate());
        mappedIssue.setResolutionDate(issueToMap.getResolutionDate());

        return mappedIssue;
    }

    public static Issue toIssue(IssueCreationDTO issueDTOToMap) {
        Issue mappedIssue = new Issue();

        mappedIssue.setTitle(issueDTOToMap.getTitle());
        mappedIssue.setDescription(issueDTOToMap.getDescription());
        mappedIssue.setTipology(issueDTOToMap.getTipology());
        mappedIssue.setImages(issueDTOToMap.getImages());

        return mappedIssue;
    }
}
