package backend.REST_controllers;

import backend.dto.UserSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @PostMapping("/{projectId}/users/collaborators")
    public ResponseEntity<Void> assignCollaboratorToProject(@PathVariable("projectId") Integer projectId, String collaboratorUsername) {
        //TODO chiamata al service
        return null;
    }

    @GetMapping("/{projectId}/addable-users")
    public ResponseEntity<List<UserSummaryDTO>> addableUsersToProject(@PathVariable("projectId") Integer projectId) {
        //TODO chiamata al service
        return null;
    }
}
