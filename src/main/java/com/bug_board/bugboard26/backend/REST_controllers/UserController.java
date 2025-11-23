package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.dto.UserCreationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody UserCreationDTO userCreationDTO){
        //TODO ritornerà la chiamata al service
        return null;
    }
}
