package com.bug_board.bugboard26.backend.services.implementations.factories;

import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.UserServiceJPA;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UserServiceFactory {

    @Value("${app.user-service.implementation}")
    private String implementation;

    private UserServiceJPA userServiceJPA;

    public UserServiceFactory(UserServiceJPA userServiceJPA) {
        this.userServiceJPA = userServiceJPA;
    }

    @Bean
    public IUserService getUserServiceImplementation() {
        if(implementation.equalsIgnoreCase("jpa")) {
            log.info("UserService implementation: jpa");
            return userServiceJPA;
        }
        else{
            log.warn("Unsopported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsopported implementation: "+implementation);
        }
    }
}
