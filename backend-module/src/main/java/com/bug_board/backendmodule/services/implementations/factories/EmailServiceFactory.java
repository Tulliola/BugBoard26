package com.bug_board.backendmodule.services.implementations.factories;

import com.bug_board.backendmodule.services.implementations.JavaEmailSenderEmailService;
import com.bug_board.backendmodule.services.interfaces.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class EmailServiceFactory {
    @Value("${app.email-service.implementation}")
    private String implementation;

    private final JavaEmailSenderEmailService javaMailSenderEmailService;

    public EmailServiceFactory(JavaEmailSenderEmailService javaMailSenderEmailService) {
        this.javaMailSenderEmailService = javaMailSenderEmailService;
    }

    @Bean
    @Primary
    public IEmailService getEmailService() {
        if(implementation.equalsIgnoreCase("javamailsender")){
            log.info("JavaMailSender implementation");
            return this.javaMailSenderEmailService;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
