package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.security.UserPrincipal;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import org.springframework.http.HttpStatus;
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

    private IProjectService projectService;

    public UserProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/overviewed")
    public ResponseEntity<List<ProjectSummaryDTO>> getOverviewedProjects(@RequestParam(required = false) String projectName, UserPrincipal principal){
        List<ProjectSummaryDTO> projectsOverviewed = projectService.getOverviewedProjects(projectName, principal.getUsername());
        return new ResponseEntity<>(projectsOverviewed, HttpStatus.OK);
    }

    @GetMapping("/working-on")
    public ResponseEntity<List<ProjectSummaryDTO>> getWorkingOnProjects(@RequestParam(required = false) String projectName, UserPrincipal principal){
        List<ProjectSummaryDTO> projectWorkingOn = projectService.getWorkingOnProjects(projectName, principal.getUsername());
        return new ResponseEntity<>(projectWorkingOn, HttpStatus.OK);
    }
}
