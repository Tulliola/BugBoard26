package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import com.bug_board.bugboard26.dto.LabelCreationDTO;

import java.util.List;

public class LabelRepositoryJPA implements ILabelRepository {
    @Override
    public void deleteLabel(Integer idLabel) {

    }

    @Override
    public void updateLabel(Integer idLabel) {

    }

    @Override
    public void createLabel(LabelCreationDTO labelToCreate) {

    }

    @Override
    public List<Label> retrieveAllUsersLabel(String username) {
        return List.of();
    }
}
