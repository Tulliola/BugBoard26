package com.bug_board.backendmodule.repositories.interfaces;

import com.bug_board.backendmodule.entity.Label;

import java.util.List;

public interface ILabelRepository {
    public void deleteLabel(Integer idLabel);
    public Label updateLabel(Label labelToUpdate);
    public Label createLabel(Label labelToCreate);
    public List<Label> retrieveAllUsersLabel(String username);
    public Label getLabelById(Integer idLabel);
    public boolean existsLabel(Integer idLabel);
}
