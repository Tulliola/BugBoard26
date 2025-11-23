package com.bug_board.bugboard26.backend.services.implementations.factories;

import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.ProjectServiceJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ProjectServiceFactory {
    @Value("${}app.project-service.implementation")
    private String implementation;

    private ProjectServiceJPA projectServiceJPA;

    public ProjectServiceFactory(ProjectServiceJPA projectServiceJPA) {
        this.projectServiceJPA = projectServiceJPA;
    }

    @Bean
    public ProjectServiceJPA projectServiceJPA() {
        if(implementation.equalsIgnoreCase("jpa")) {
            log.info("JPA implementation");
            return projectServiceJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
