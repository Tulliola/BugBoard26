package com.bug_board.backendmodule.services.implementations.factories;

import com.bug_board.backendmodule.services.implementations.jpa_implementations.AuthenticationServiceJPA;
import com.bug_board.backendmodule.services.interfaces.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class AuthenticationServiceFactory {
    @Value("${app.authentication-service.implementation}")
    private String implementation;

    private final AuthenticationServiceJPA authenticationServiceJPA;

    public AuthenticationServiceFactory(AuthenticationServiceJPA authenticationServiceJPA) {
        this.authenticationServiceJPA = authenticationServiceJPA;
    }

    @Bean
    @Primary
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
