package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.security.UserPrincipal;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.dto.IssueSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/me/issues")
public class UserIssueController {

    private final IIssueService issueService;

    public UserIssueController(IIssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("")
    public ResponseEntity<List<IssueSummaryDTO>> getPersonalIssues(@AuthenticationPrincipal UserPrincipal principal) {
        List<IssueSummaryDTO> usersIssues = issueService.getIssuesOfAUser(principal.getUsername());
        return new ResponseEntity<>(usersIssues, HttpStatus.OK);
    }
}
