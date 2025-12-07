package com.bug_board.backendmodule.REST_controllers;

import com.bug_board.backendmodule.services.interfaces.IEmailService;
import com.bug_board.dto.EmailToSendDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class EmailSenderController {

    private final IEmailService emailService;

    public EmailSenderController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailToSendDTO email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }
}
