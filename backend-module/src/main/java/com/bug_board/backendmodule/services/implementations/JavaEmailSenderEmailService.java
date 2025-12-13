package com.bug_board.backendmodule.services.implementations;

import com.bug_board.backendmodule.exception.backend.MalformedMailException;
import com.bug_board.backendmodule.services.interfaces.IEmailService;
import com.bug_board.dto.email.IEmailToSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailSenderEmailService implements IEmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    public JavaEmailSenderEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public void sendWelcomeEmail(IEmailToSendDTO email) {
        if(email == null)
            throw new MalformedMailException("Email is null.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email.getAddressee());
        message.setSubject("Your BugBoard26 Credentials");
        message.setText("Your BugBoard26 Credentials are:\nUsername: "+ email.getUsername() +"\nPassword: "+ email.getPassword());

        if(email.getAddressee() == null || email.getAddressee().isEmpty())
            throw new MalformedMailException("Must specify the addressee.");

        mailSender.send(message);
    }
}
