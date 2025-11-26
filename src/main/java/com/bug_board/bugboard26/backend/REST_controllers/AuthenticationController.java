package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.services.interfaces.IAuthenticationService;
import com.bug_board.bugboard26.dto.UserAuthenticationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IAuthenticationService authService;

    public AuthenticationController(IAuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDTO userAuthentication) {
        String jwtToken = authService.verify(userAuthentication);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
