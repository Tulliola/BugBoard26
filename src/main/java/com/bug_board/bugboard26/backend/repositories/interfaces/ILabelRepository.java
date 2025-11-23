package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.dto.LabelCreationDTO;

import java.util.List;

public interface ILabelRepository {
    public void deleteLabel(Integer idLabel);
    public void updateLabel(Integer idLabel);
    public void createLabel(LabelCreationDTO labelToCreate);
    public List<Label> retrieveAllUsersLabel(String username);
}
