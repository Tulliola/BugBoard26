package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserLabelDAO;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.exceptions.architectural_controllers.LabelDeleteException;
import com.bug_board.exceptions.architectural_controllers.LabelModifyingException;
import com.bug_board.exceptions.architectural_controllers.RetrieveLabelsException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.awt.*;
import java.util.List;

public class UserLabelController {
    private final IUserLabelDAO userLabelDAO;

    public UserLabelController(IUserLabelDAO userLabelDAO) {
        this.userLabelDAO = userLabelDAO;
    }

    public void createNewLabel(LabelCreationDTO labelToCreate)
            throws LabelCreationException {
        try {
            userLabelDAO.createNewLabel(labelToCreate);
        } catch (BadConversionToJSONException | HTTPSendException | BadConversionToDTOException| ErrorHTTPResponseException throwables) {
            throw new LabelCreationException("Couldn't create the label. Please, try later.");
        }
    }

    public void deleteLabel(Integer idLabel)
            throws LabelDeleteException {
        try {
            userLabelDAO.deleteLabel(idLabel);
        }
        catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException throwables) {
            throw new LabelDeleteException("Couldn't delete the label. Please, try later.");
        }
    }

    public void modifyLabel(LabelModifyingDTO modifyingDTO)
            throws LabelModifyingException {
        try {
            userLabelDAO.modifyLabel(modifyingDTO);
        }
        catch (BadConversionToJSONException | HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException throwables) {
            throw new LabelModifyingException("Couldn't modify the label. Please, try later.");
        }
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        try {
            return userLabelDAO.getLabels();
        } catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException e) {
            throw new RetrieveLabelsException(e.getMessage());
        }
    }
}
