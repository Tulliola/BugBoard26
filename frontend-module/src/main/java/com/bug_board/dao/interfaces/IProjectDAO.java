package com.bug_board.dao.interfaces;

import com.bug_board.dto.CollaboratorAssociationDTO;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IProjectDAO {
    UserSummaryDTO assignCollaboratorToProject(Integer idProject, CollaboratorAssociationDTO collaborator)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException, BadConversionToJSONException;

    public abstract List<UserSummaryDTO> getAddableUsersToProject(Integer idProject)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
}
