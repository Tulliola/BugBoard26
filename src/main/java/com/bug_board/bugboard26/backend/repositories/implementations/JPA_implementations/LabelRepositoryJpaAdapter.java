package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
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
//       return labelRepositoryJPA.findAllByUsername(username);
        return null;
    }
}
