package com.bug_board.backendmodule.exception;

import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.backendmodule.exception.backend.MalformedMailException;
import com.bug_board.backendmodule.exception.backend.ResourceAlreadyExistsException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationFailedException(AuthenticationException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponseDTO> handleMailException(MailException exc) {
        // 1. Caso: L'utente ha inserito un'email non valida (che è sfuggita alla validazione DTO)
        ErrorResponseDTO error = new ErrorResponseDTO();
        int status;
        String phrase;
        String message;

        if (exc instanceof MailParseException) {
            status = HttpStatus.BAD_REQUEST.value();
            phrase = HttpStatus.BAD_REQUEST.getReasonPhrase();
            message = "Email specified is not valid.";
        }
        else if (exc instanceof MailAuthenticationException) {
            status =  HttpStatus.INTERNAL_SERVER_ERROR.value();
            phrase = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            message = "Internal server error.";
        }
        else {
            status =  HttpStatus.INTERNAL_SERVER_ERROR.value();
            phrase = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            message = "Cannot send the email. Try later.";
        }

        error.setPhrase(phrase);
        error.setStatus(status);
        error.setError(message);

        return new ResponseEntity<>(error, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(MalformedMailException.class)
    public ResponseEntity<ErrorResponseDTO> handleMalformedMailException(MalformedMailException exc) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
