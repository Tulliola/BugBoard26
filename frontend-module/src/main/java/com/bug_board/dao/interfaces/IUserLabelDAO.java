package com.bug_board.dao.interfaces;

import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserLabelDAO {
    public abstract LabelSummaryDTO createNewLabel(LabelCreationDTO labelToCreate)
            throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
    public abstract void deleteLabel(Integer idLabel) throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
    public abstract LabelSummaryDTO modifyLabel(LabelModifyingDTO labelToModify)
            throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
    public abstract List<LabelSummaryDTO> getLabels()
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
}
