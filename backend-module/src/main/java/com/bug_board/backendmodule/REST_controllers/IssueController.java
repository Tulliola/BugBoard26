package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.security.UserPrincipal;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{project-id}/issues")
public class IssueController {

    private final IIssueService issueService;

    public IssueController(IIssueService issueService) {
        this.issueService = issueService;
    }


    @GetMapping("")
    public ResponseEntity<List<IssueSummaryDTO>> getAllProjectsIssues(@PathVariable ("project-id") Integer projectId){
        List<IssueSummaryDTO> issueSummaryDTOS = issueService.getIssuesOfAProject(projectId);
        return new ResponseEntity<>(issueSummaryDTOS, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<IssueSummaryDTO> createNewIssue(@AuthenticationPrincipal UserPrincipal principal,
                                                          @PathVariable("project-id") Integer projectId,
                                                          @RequestBody IssueCreationDTO issue){
        IssueSummaryDTO issueCreated = issueService.publishNewIssueToProject(principal.getUsername(), projectId, issue);
        return new ResponseEntity<>(issueCreated, HttpStatus.CREATED);
    }
}
