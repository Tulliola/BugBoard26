package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{project-id}/issues")
public class IssueController {

    private final IIssueService issueService;

    public IssueController(IIssueService issueService) {
        this.issueService = issueService;
    }


    @GetMapping()
    public ResponseEntity<List<IssueSummaryDTO>> getAllProjectsIssues(@PathVariable ("project-id") Integer projectId){
        try {
            return new ResponseEntity<>(issueService.getIssueOfAProject(projectId), HttpStatus.OK);
        }
        catch (Exception e) {
            //TODO cambiare una volta definite le eccezioni nel service
            return new ResponseEntity<>(HttpStatus.TOO_EARLY);
        }
    }

    @PostMapping()
    public ResponseEntity<IssueSummaryDTO> createNewIssue(@PathVariable("project-id") Integer projectId, @RequestBody IssueCreationDTO issueCreationDTO){
        try {
            return new ResponseEntity<>(issueService.publishNewIssueToProject(issueCreationDTO), HttpStatus.CREATED);
        }
        catch (Exception e){
            //TODO cambiare una volta definite le eccezioni nel service
            return new ResponseEntity<>(HttpStatus.TOO_EARLY);
        }
    }
}
