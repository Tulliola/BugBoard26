package com.bug_board.backendmodule.repositories.implementations.factories;

import com.bug_board.backendmodule.repositories.implementations.jpa_implementations.UserRepositoryJpaAdapter;
import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class UserRepositoryFactory {
    @Value("${app.user-repository.implementation}")
    private String implementation;

    private final UserRepositoryJpaAdapter userRepositoryJPA;

    public UserRepositoryFactory(UserRepositoryJpaAdapter userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Bean
    @Primary
    public IUserRepository getUserRepository() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return this.userRepositoryJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
