package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;

import java.util.List;

public interface ILabelService {
    public LabelSummaryDTO createPersonalLabel(LabelCreationDTO labelToCreate);
    public void deletePersonalLabel(Integer idLabel);
    public LabelSummaryDTO modifyPersonalLabel(LabelCreationDTO labelToModify);
    public List<LabelSummaryDTO> getPersonalLabels(String username);
}
