package com.bug_board.backendmodule.services.implementations.factories;

import com.bug_board.backendmodule.services.implementations.jpa_implementations.UserServiceJPA;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class UserServiceFactory {

    @Value("${app.user-service.implementation}")
    private String implementation;

    private final UserServiceJPA userServiceJPA;

    public UserServiceFactory(UserServiceJPA userServiceJPA) {
        this.userServiceJPA = userServiceJPA;
    }

    @Bean
    @Primary
    public IUserService getUserService() {
        if(implementation.equalsIgnoreCase("jpa")) {
            log.info("JPA implementation");
            return userServiceJPA;
        }
        else{
            log.warn("Unsopported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsopported implementation: "+implementation);
        }
    }
}
