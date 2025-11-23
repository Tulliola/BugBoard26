package com.bug_board.bugboard26.backend.REST_controllers;

import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor(onConstructor=@__(@Autowired))
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody UserCreationDTO userCreationDTO){
        //TODO ritornerà la chiamata al service
        return null;
    }
}
