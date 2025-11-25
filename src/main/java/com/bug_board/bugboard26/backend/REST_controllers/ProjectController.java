package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/{projectId}/users/collaborators")
    public ResponseEntity<UserSummaryDTO> assignCollaboratorToProject(@PathVariable("projectId") Integer projectId, String collaboratorUsername) {
        //TODO chiamata al service
        return null;
    }

    @GetMapping("/{projectId}/addable-users")
    public ResponseEntity<List<UserSummaryDTO>> addableUsersToProject(@PathVariable("projectId") Integer projectId) {
        //TODO chiamata al service
        return null;
    }
}
