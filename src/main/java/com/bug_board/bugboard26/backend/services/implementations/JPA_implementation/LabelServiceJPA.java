package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceJPA implements ILabelService {
    @Override
    public void createPersonalLabel(LabelCreationDTO labelToCreate) {

    }

    @Override
    public void deletePersonalLabel(Integer idLabel) {

    }

    @Override
    public void modifyPersonalLabel(LabelCreationDTO labelToModify) {

    }

    @Override
    public List<LabelSummaryDTO> getPersonalLabels(String username) {
        return List.of();
    }
}
