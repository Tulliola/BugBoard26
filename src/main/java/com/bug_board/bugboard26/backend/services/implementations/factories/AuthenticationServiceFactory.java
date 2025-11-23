package com.bug_board.bugboard26.backend.services.implementations.factories;

import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.AuthenticationServiceJPA;
import com.bug_board.bugboard26.backend.services.interfaces.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AuthenticationServiceFactory {
    @Value("${app.authentication-service.implementation}")
    private String implementation;

    private AuthenticationServiceJPA authenticationServiceJPA;

    public AuthenticationServiceFactory(AuthenticationServiceJPA authenticationServiceJPA) {
        this.authenticationServiceJPA = authenticationServiceJPA;
    }

    @Bean
    public IAuthenticationService getAuthenticationService() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return this.authenticationServiceJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
