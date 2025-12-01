package com.bug_board.dao.interfaces;

import com.bug_board.dto.TokenJWTDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.io.IOException;

public interface IAuthenticationDAO {
    public abstract TokenJWTDTO authenticate(UserAuthenticationDTO dto) throws IOException, InterruptedException, BadConversionToJSONException, BadConversionToDTOException, HTTPSendException, BackendErrorException;
}
