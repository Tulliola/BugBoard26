package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceJPA implements ILabelService {

    private final ILabelRepository labelRepository;

    public LabelServiceJPA(ILabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public LabelSummaryDTO createPersonalLabel(LabelCreationDTO labelToCreate) {
        return null;
    }

    @Override
    public void deletePersonalLabel(Integer idLabel) {

    }

    @Override
    public LabelSummaryDTO modifyPersonalLabel(LabelCreationDTO labelToModify) {
        return null;
    }

    @Override
    public List<LabelSummaryDTO> getPersonalLabels(String username) {
        return List.of();
    }
}
