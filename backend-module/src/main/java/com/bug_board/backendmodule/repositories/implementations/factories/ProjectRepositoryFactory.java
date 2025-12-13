package com.bug_board.backendmodule.repositories.implementations.factories;

import com.bug_board.backendmodule.repositories.implementations.jpa_implementations.ProjectRepositoryJpaAdapter;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
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
