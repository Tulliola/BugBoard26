package com.bug_board.bugboard26.backend.repositories.implementations.factories;

import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.IssueRepositoryJPA;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.AuthenticationServiceJPA;
import com.bug_board.bugboard26.backend.services.interfaces.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class IssueRepositoryFactory {
    @Value("${app.issue-repository.implementation}")
    private String implementation;

    private final IssueRepositoryJPA issueRepositoryJPA;

    public IssueRepositoryFactory(IssueRepositoryJPA issueRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
    }

    @Bean
    @Primary
    public IIssueRepository getIssueRepository() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return this.issueRepositoryJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
