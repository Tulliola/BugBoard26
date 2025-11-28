package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.CollaboratorAssociationDTO;
import com.bug_board.dto.UserSummaryDTO;
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
    public ResponseEntity<UserSummaryDTO> assignCollaboratorToProject(@PathVariable("projectId") Integer projectId, @RequestBody CollaboratorAssociationDTO collaboratorUsername) {
        UserSummaryDTO collaborator = projectService.assignCollaboratorToProject(projectId, collaboratorUsername.getCollaboratorUsername());
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/addable-users")
    public ResponseEntity<List<UserSummaryDTO>> getAddableUsersToProject(@PathVariable("projectId") Integer projectId) {
        List<UserSummaryDTO> addableUsers = userService.getAddableUsersToProject(projectId);
        return new ResponseEntity<>(addableUsers, HttpStatus.OK);
    }
}
