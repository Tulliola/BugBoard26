package com.bug_board.backendmodule.mappers;

import com.bug_board.backendmodule.entity.Issue;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.LabelSummaryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IssueMapper {
    private IssueMapper() {

    }

    public static IssueSummaryDTO toIssueSummaryDTO(Issue issueToMap) {
        IssueSummaryDTO mappedIssue = new IssueSummaryDTO();

        mappedIssue.setIdIssue(issueToMap.getIdIssue());
        mappedIssue.setTitle(issueToMap.getTitle());
        mappedIssue.setDescription(issueToMap.getDescription());
        mappedIssue.setState(issueToMap.getState());
        mappedIssue.setTipology(issueToMap.getTipology());
        mappedIssue.setCreatorName(issueToMap.getCreator().getUsername());
        mappedIssue.setCreatorBioPic(issueToMap.getCreator().getBioPic());
        mappedIssue.setCreationDate(issueToMap.getCreationDate());
        mappedIssue.setResolutionDate(issueToMap.getResolutionDate());
        mappedIssue.setPriority(issueToMap.getPriority());
        mappedIssue.setLabels(LabelMapper.toLabelSummaryDTOS(issueToMap.getAttachedLabels()));

        return mappedIssue;
    }

    public static Issue toIssue(IssueCreationDTO issueDTOToMap) {
        Issue mappedIssue = new Issue();

        mappedIssue.setTitle(issueDTOToMap.getTitle());
        mappedIssue.setDescription(issueDTOToMap.getDescription());
        mappedIssue.setTipology(issueDTOToMap.getTipology());
        mappedIssue.setImages(issueDTOToMap.getImages());
        mappedIssue.setState(issueDTOToMap.getState());

        return mappedIssue;
    }

    public static List<IssueSummaryDTO> toIssueSummaryDTOS(List<Issue> issues){
        return issues.stream()
                .map(IssueMapper::toIssueSummaryDTO)
                .toList();
    }
}
