package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.backend.services.mappers.LabelMapper;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelModifyingDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;
import com.bug_board.bugboard26.exception.backend.BadRequestException;
import com.bug_board.bugboard26.exception.backend.ResourceNotFoundException;
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
        Label labelCreated = labelRepository.createLabel(LabelMapper.labelCreationDTOtoLabel(labelToCreate));
        return LabelMapper.toDTO(labelCreated);
    }

    @Override
    public void deletePersonalLabel(Integer idLabel) {
        if(labelRepository.existsLabel(idLabel))
            labelRepository.deleteLabel(idLabel);
        else
            throw new ResourceNotFoundException("Label with specified id not found");
    }

    @Override
    public LabelSummaryDTO modifyPersonalLabel(Integer idLabel, LabelModifyingDTO labelToModify) {
        if(labelRepository.existsLabel(labelToModify.getIdLabel())) {
            if(!(idLabel.equals(labelToModify.getIdLabel()))) {
                throw new BadRequestException("Id Label in URL and Id Label in label specified do not match");
            }
            Label labelModified = labelRepository.updateLabel(LabelMapper.labelModifyingDTOtoLabel(labelToModify));
            return LabelMapper.toDTO(labelModified);
        }
        else
            throw new ResourceNotFoundException("Label with specified id not found");
    }

    @Override
    public List<LabelSummaryDTO> getPersonalLabels(String username) {
        List<Label> personalLabels = labelRepository.retrieveAllUsersLabel(username);
        return personalLabels.stream().map(LabelMapper::toDTO).toList();
    }

    @Override
    public Label getLabel(Integer idLabel) {
        if(labelRepository.getLabelById(idLabel) == null)
            throw new ResourceNotFoundException("Label Not Found");

        return labelRepository.getLabelById(idLabel);
    }
}
