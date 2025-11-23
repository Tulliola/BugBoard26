package com.bug_board.bugboard26.backend.repositories.implementations.factories;

import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.IssueRepositoryJpaAdapter;
import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.ProjectRepositoryJpaAdapter;
import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.UserRepositoryJpaAdapter;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class ProjectRepositoryFactory {
    @Value("${app.project-repository.implementation}")
    private String implementation;

    private final ProjectRepositoryJpaAdapter projectRepositoryJPA;

    public ProjectRepositoryFactory(ProjectRepositoryJpaAdapter projectRepositoryJPA) {
        this.projectRepositoryJPA = projectRepositoryJPA;
    }

    @Bean
    @Primary
    public IProjectRepository getProjectRepository() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return this.projectRepositoryJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
