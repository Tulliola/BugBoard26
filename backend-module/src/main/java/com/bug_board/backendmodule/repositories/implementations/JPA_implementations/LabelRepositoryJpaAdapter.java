package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabelRepositoryJpaAdapter implements ILabelRepository {
    private final ILabelRepositoryJPA labelRepositoryJPA;

    public LabelRepositoryJpaAdapter(ILabelRepositoryJPA labelRepositoryJPA) {
        this.labelRepositoryJPA = labelRepositoryJPA;
    }

    @Override
    public void deleteLabel(Integer idLabel) {
        labelRepositoryJPA.deleteById(idLabel);
    }

    @Override
    public Label updateLabel(Label label) {
       return labelRepositoryJPA.save(label);
    }

    @Override
    public Label createLabel(Label labelToCreate) {
        return labelRepositoryJPA.save(labelToCreate);
    }

    @Override
    public List<Label> retrieveAllUsersLabel(String username) {
        return labelRepositoryJPA.findAllByUsername(username);
    }

    @Override
    public Label getLabelById(Integer idLabel) {
        return labelRepositoryJPA.findById(idLabel).orElse(null);
    }

    @Override
    public boolean existsLabel(Integer idLabel) {
        return labelRepositoryJPA.existsById(idLabel);
    }
}
