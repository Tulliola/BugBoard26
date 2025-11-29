package com.bug_board.dao.interfaces;

import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;

import java.util.List;

public interface IUserLabelDAO {
    public abstract LabelSummaryDTO createNewLabel(LabelCreationDTO labelToCreate);
    public abstract void deleteLabel(Integer idLabel);
    public abstract LabelSummaryDTO modifyLabel(LabelModifyingDTO labelToModify);
    public abstract List<LabelSummaryDTO> getLabels();
}
