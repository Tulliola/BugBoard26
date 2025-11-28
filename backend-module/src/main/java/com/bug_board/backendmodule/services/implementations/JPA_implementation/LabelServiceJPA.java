package com.bug_board.backendmodule.services.implementations.JPA_implementation;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.mappers.LabelMapper;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceJPA implements ILabelService {

    private final ILabelRepository labelRepository;

    private final IUserService userService;

    public LabelServiceJPA(ILabelRepository labelRepository, IUserService userService) {
        this.userService = userService;
        this.labelRepository = labelRepository;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public LabelSummaryDTO createPersonalLabel(String usernamePrincipal, LabelCreationDTO labelToCreate) {
        if(labelToCreate.getColor() == null)
            labelToCreate.setColor("#FFFFFF");

        User creator = userService.findUserByUsername(usernamePrincipal);

        if(creator == null)
            throw new ResourceNotFoundException("User not found");

        Label mappedLabel = LabelMapper.labelCreationDTOtoLabel(labelToCreate);
        mappedLabel.setCreator(creator);

        Label labelCreated = labelRepository.createLabel(mappedLabel);

        return LabelMapper.toDTO(labelCreated);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePersonalLabel(String usernamePrincipal ,Integer idLabel) {
        if(labelRepository.existsLabel(idLabel)) {

            Label baseLabel =  labelRepository.getLabelById(idLabel);

            if(baseLabel == null)
                throw new ResourceNotFoundException("Label not found");

            if(!usernamePrincipal.equals(baseLabel.getCreatorUsername()))
                throw new AccessDeniedException("The label you want to delete is not yours.");

            labelRepository.deleteLabel(idLabel);
        }
        else
            throw new ResourceNotFoundException("Label with specified id not found");
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public LabelSummaryDTO modifyPersonalLabel(String usernamePrincipal, Integer idLabel, LabelModifyingDTO labelToModify) {
        if(labelRepository.existsLabel(labelToModify.getIdLabel())) {
            if(!(idLabel.equals(labelToModify.getIdLabel())))
                throw new BadRequestException("Id Label in URL and Id Label in label specified do not match");

            if(labelToModify.getColor() == null)
                labelToModify.setColor("#FFFFFF");

            Label baseLabel = labelRepository.getLabelById(labelToModify.getIdLabel());

            if(baseLabel == null)
                throw new ResourceNotFoundException("Label not found");

            if(!usernamePrincipal.equals(baseLabel.getCreatorUsername()))
                throw new AccessDeniedException("The label you want to modify it's not yours.");

            Label mappedLabel =  LabelMapper.labelModifyingDTOtoLabel(labelToModify);
            mappedLabel.setCreator(userService.findUserByUsername(usernamePrincipal));

            Label labelModified = labelRepository.updateLabel(mappedLabel);

            return LabelMapper.toDTO(labelModified);
        }
        else
            throw new ResourceNotFoundException("Label with specified id not found");
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<LabelSummaryDTO> getPersonalLabels(String username) {
        List<Label> personalLabels = labelRepository.retrieveAllUsersLabel(username);
        return LabelMapper.toLabelSummaryDTOS(personalLabels);
    }

    @Override
    public Label getLabel(Integer idLabel) {
        if(labelRepository.getLabelById(idLabel) == null)
            throw new ResourceNotFoundException("Label Not Found");

        return labelRepository.getLabelById(idLabel);
    }
}
