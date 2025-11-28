package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;

import java.util.List;

public interface ILabelService {
    public LabelSummaryDTO createPersonalLabel(String usernamePrincipal, LabelCreationDTO labelToCreate);
    public void deletePersonalLabel(String usernamePrincipal, Integer idLabel);
    public LabelSummaryDTO modifyPersonalLabel(String usernamePrincipal, Integer idLabel, LabelModifyingDTO labelToModify);
    public List<LabelSummaryDTO> getPersonalLabels(String username);
    public Label getLabel(Integer idLabel);
}
