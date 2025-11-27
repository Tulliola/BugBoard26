package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.entity.Issue;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.security.UserPrincipal;
import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelModifyingDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("")
    public ResponseEntity<LabelSummaryDTO> createNewLabel(@AuthenticationPrincipal UserPrincipal principal,
                                                          @RequestBody LabelCreationDTO labelToCreate) {
        LabelSummaryDTO labelCreated = labelService.createPersonalLabel(principal.getUsername(), labelToCreate);
        return new ResponseEntity<>(labelCreated, HttpStatus.OK);
    }

    @DeleteMapping("/{idLabel}")
    public ResponseEntity<Void> deleteLabel(@AuthenticationPrincipal UserPrincipal principal,
                                            @PathVariable("idLabel") Integer idLabel) {
        labelService.deletePersonalLabel(principal.getUsername(), idLabel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{idLabel}")
    public ResponseEntity<LabelSummaryDTO>  updateLabel(@AuthenticationPrincipal UserPrincipal principal,
                                                        @PathVariable("idLabel") Integer idLabel,
                                                        @RequestBody LabelModifyingDTO labelToUpdate) {
        LabelSummaryDTO labelUpdated = labelService.modifyPersonalLabel(principal.getUsername(), idLabel, labelToUpdate);
        return new ResponseEntity<>(labelUpdated, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<LabelSummaryDTO>> getLabels(@AuthenticationPrincipal UserPrincipal principal) {
        List<LabelSummaryDTO> usersLabels = labelService.getPersonalLabels(principal.getUsername());
        return new ResponseEntity<>(usersLabels, HttpStatus.OK);
    }
}
