package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/me/projects")
public class UserProjectController {
    @GetMapping("/overviewed")
    public ResponseEntity<List<ProjectSummaryDTO>> getOverviewedProjects(@RequestParam(required = false) String projectName, Principal principal){
        //TODO chiamata al service
        return null;
    }

    @GetMapping("/working-on")
    public ResponseEntity<List<ProjectSummaryDTO>> getWorkingOnProjects(@RequestParam(required = false) String projectName, Principal principal){
        //TODO chiamata al service
        return null;
    }
}
