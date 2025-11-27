package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.backend.security.UserPrincipal;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelModifyingDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;

import java.util.List;

public interface ILabelService {
    public LabelSummaryDTO createPersonalLabel(String usernamePrincipal, LabelCreationDTO labelToCreate);
    public void deletePersonalLabel(String usernamePrincipal, Integer idLabel);
    public LabelSummaryDTO modifyPersonalLabel(String usernamePrincipal, Integer idLabel, LabelModifyingDTO labelToModify);
    public List<LabelSummaryDTO> getPersonalLabels(String username);
    public Label getLabel(Integer idLabel);
}
