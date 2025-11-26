package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final IProjectService projectService;

    private final IUserService userService;

    public ProjectController(IProjectService projectService, IUserService userService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping("/{projectId}/users/collaborators")
    public ResponseEntity<UserSummaryDTO> assignCollaboratorToProject(@PathVariable("projectId") Integer projectId, String collaboratorUsername) {
        UserSummaryDTO collaborator = projectService.assignCollaboratorToProject(projectId, collaboratorUsername);
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/addable-users")
    public ResponseEntity<List<UserSummaryDTO>> getAddableUsersToProject(@PathVariable("projectId") Integer projectId) {
        List<UserSummaryDTO> addableUsers = userService.getAddableUsersToProject(projectId);
        return new ResponseEntity<>(addableUsers, HttpStatus.OK);
    }
}
