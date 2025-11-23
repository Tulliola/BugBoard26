package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.dto.IssueCreationDTO;
import com.bug_board.bugboard26.backend.dto.IssueSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{project-id}/issues")
public class IssueController {
    @GetMapping()
    public ResponseEntity<List<IssueSummaryDTO>> getAllProjectsIssues(@PathVariable ("project-id") Integer projectId){
        //TODO ritornerà la chiamata al service
        return null;
    }

    @PostMapping()
    public ResponseEntity<Void> createNewIssue(@PathVariable("project-id") Integer projectId, @RequestBody IssueCreationDTO issueCreationDTO){
        //TODO ritornerà la chiamata al service
        return null;
    }
}
