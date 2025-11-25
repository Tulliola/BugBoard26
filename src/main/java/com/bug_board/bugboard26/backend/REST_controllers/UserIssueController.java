package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.security.UserPrincipal;
import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/me/issues")
public class UserIssueController {

    private final IIssueService issueService;

    public UserIssueController(IIssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public ResponseEntity<List<IssueSummaryDTO>> getPersonalIssues(UserPrincipal principal) {
        //TODO chiamata al service
        return null;
    }
}
