package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.security.UserPrincipal;
import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelModifyingDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/me/labels")
public class UserLabelController {

    private final ILabelService labelService;

    public UserLabelController(ILabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<LabelSummaryDTO> createNewLabel(LabelCreationDTO labelToCreate) {
        //TODO chiamata al service
        return null;
    }

    @DeleteMapping("/{idLabel}")
    public ResponseEntity<Void> deleteLabel(@PathVariable("idLabel") Integer idLabel) {
        //TODO chiamata al service
        //TODO discutere sul codice di ritorno 204 e best practice
        return null;
    }

    @PutMapping("/{idLabel}")
    public ResponseEntity<LabelSummaryDTO>  updateLabel(@PathVariable("idLabel") Integer idLabel, @RequestBody LabelModifyingDTO labelToUpdate) {
        //TODO chiamata al service
        return null;
    }

    @GetMapping()
    public ResponseEntity<List<LabelSummaryDTO>> getLabels(UserPrincipal principal) {
        //TODO chiamata al service
        return null;
    }
}
