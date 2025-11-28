package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserSummaryDTO> registerNewUser(@RequestBody UserCreationDTO userCreationDTO){
        UserSummaryDTO userSummaryDTO = userService.registerNewUser(userCreationDTO);
        return new ResponseEntity<>(userSummaryDTO, HttpStatus.CREATED);
    }
}
