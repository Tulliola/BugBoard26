package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserLabelDAO;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.awt.*;

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
            throw new LabelCreationException("Couldn't create the label.");
        }
    }
}
