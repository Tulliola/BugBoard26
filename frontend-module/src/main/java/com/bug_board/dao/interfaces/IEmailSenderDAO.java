package com.bug_board.dao.interfaces;

import com.bug_board.dto.email.EmailToSendDTO;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

public interface IEmailSenderDAO {
    public abstract void send(EmailToSendDTO dto)
            throws BadConversionToJSONException, ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException;
}
