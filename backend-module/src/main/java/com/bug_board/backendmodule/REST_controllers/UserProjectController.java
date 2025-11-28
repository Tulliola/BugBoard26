package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.security.UserPrincipal;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.dto.ProjectSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/me/projects")
public class UserProjectController {

    private IProjectService projectService;

    public UserProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/overviewed")
    public ResponseEntity<List<ProjectSummaryDTO>> getOverviewedProjects(@RequestParam(required = false) String projectName,
                                                                         @AuthenticationPrincipal UserPrincipal principal){
        List<ProjectSummaryDTO> projectsOverviewed = projectService.getOverviewedProjects(principal.getUsername(), projectName);
        return new ResponseEntity<>(projectsOverviewed, HttpStatus.OK);
    }

    @GetMapping("/working-on")
    public ResponseEntity<List<ProjectSummaryDTO>> getWorkingOnProjects(@RequestParam(required = false) String projectName,
                                                                        @AuthenticationPrincipal UserPrincipal principal){
        List<ProjectSummaryDTO> projectWorkingOn = projectService.getWorkingOnProjects(principal.getUsername(), projectName);
        return new ResponseEntity<>(projectWorkingOn, HttpStatus.OK);
    }
}
