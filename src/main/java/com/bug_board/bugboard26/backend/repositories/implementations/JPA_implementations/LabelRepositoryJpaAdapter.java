package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabelRepositoryJpaAdapter implements ILabelRepository {
    private final ILabelRepositoryJPA repository;

    public LabelRepositoryJpaAdapter(ILabelRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public void deleteLabel(Integer idLabel) {
        repository.deleteById(idLabel);
    }

    @Override
    public void updateLabel(Label label) {
        //TODO DISCUTERE CON FULVIO SU TIPI DI RITORNO E PARAMETRI
        repository.save(label);
    }

    @Override
    public void createLabel(Label labelToCreate) {
        repository.save(labelToCreate);
    }

    @Override
    public List<Label> retrieveAllUsersLabel(String username) {
       return repository.findAllByUsername(username);
    }
}
