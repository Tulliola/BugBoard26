package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/me/issues")
public class UserIssueController {
    @GetMapping
    public List<IssueSummaryDTO> getPersonalIssues(Principal principal) {
        //TODO chiamata al service
        return null;
    }
}
