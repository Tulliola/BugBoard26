package com.bug_board.backendmodule.services.implementations;

import com.bug_board.backendmodule.exception.backend.MalformedMailException;
import com.bug_board.backendmodule.services.interfaces.IEmailService;
import com.bug_board.dto.EmailToSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailSenderEmailService implements IEmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    JavaMailSender mailSender;

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public void sendEmail(EmailToSendDTO email) {
        if(email == null)
            throw new MalformedMailException("Email is null.");

        if(email.getAddressee() == null || email.getAddressee().isEmpty())
            throw new MalformedMailException("Must specify the addressee.");

        if(email.getSubject() == null || email.getSubject().isEmpty())
            throw new MalformedMailException("Must specify the subject.");

        if(email.getBody() == null || email.getBody().isEmpty())
            throw new MalformedMailException("Must specify the body.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email.getAddressee());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
    }
}
