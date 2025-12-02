package com.bug_board.dao.interfaces;

import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

public interface IUserDAO {
    public UserSummaryDTO registerNewUser(UserCreationDTO userToRegister)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException, BadConversionToJSONException;
}
