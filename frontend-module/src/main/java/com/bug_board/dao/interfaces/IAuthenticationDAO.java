package com.bug_board.dao.interfaces;

import com.bug_board.dto.LoginResponseDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

public interface IAuthenticationDAO {
    public abstract LoginResponseDTO authenticate(UserAuthenticationDTO dto) throws BadConversionToJSONException, BadConversionToDTOException, HTTPSendException, ErrorHTTPResponseException;
}
