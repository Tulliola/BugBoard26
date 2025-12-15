package com.bug_board.backendmodule.rest_controllers;

import com.bug_board.backendmodule.security.UserPrincipal;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me/issues")
public class UserIssueController {

    private final IIssueService issueService;

    public UserIssueController(IIssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<IssueSummaryDTO>> getPersonalIssues(@AuthenticationPrincipal UserPrincipal principal,
                                                                   @RequestBody IssueFiltersDTO filters) {
        List<IssueSummaryDTO> usersIssues = issueService.getIssuesOfAUser(principal.getUsername(), filters);
        return new ResponseEntity<>(usersIssues, HttpStatus.OK);
    }

    @GetMapping("/{issue-id}/images")
    public ResponseEntity<List<IssueImageDTO>> getAllIssueImages(@AuthenticationPrincipal UserPrincipal principal,
                                                                 @PathVariable ("issue-id") Integer issueId){
        List<IssueImageDTO> issueImageDTOS = issueService.getImagesOfAIssue(principal.getUsername(), issueId);
        return new ResponseEntity<>(issueImageDTOS, HttpStatus.CREATED);
    }

}
