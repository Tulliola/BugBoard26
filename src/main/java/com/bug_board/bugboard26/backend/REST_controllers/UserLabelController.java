package com.bug_board.bugboard26.backend.REST_controllers;

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
    @PostMapping
    public ResponseEntity<Void> createNewLabel(LabelCreationDTO labelToCreate) {
        //TODO chiamata al service
        return null;
    }

    @DeleteMapping("/{idLabel")
    public ResponseEntity<Void> deleteLabel(@PathVariable("idLabel") Integer idLabel) {
        //TODO chiamata al service
        return null;
    }

    @PutMapping("/{idLabel")
    public ResponseEntity<Void>  updateLabel(@PathVariable("idLabel") Integer idLabel, @RequestBody LabelModifyingDTO labelToUpdate) {
        //TODO chiamata al service
        return null;
    }

    @GetMapping
    public ResponseEntity<List<LabelSummaryDTO>> getLabels(Principal principal) {
        //TODO chiamata al service
        return null;
    }
}
