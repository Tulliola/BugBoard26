package com.bug_board.bugboard26.backend.repositories.implementations.factories;

import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.IssueRepositoryJpaAdapter;
import com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations.LabelRepositoryJpaAdapter;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import com.bug_board.bugboard26.backend.repositories.interfaces.ILabelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.prepost.PreAuthorize;

@Configuration
@Slf4j
public class LabelRepositoryFactory {
    @Value("${app.label-repository.implementation}")
    private String implementation;

    private final LabelRepositoryJpaAdapter labelRepositoryJPA;

    public LabelRepositoryFactory(LabelRepositoryJpaAdapter labelRepositoryJPA) {
        this.labelRepositoryJPA = labelRepositoryJPA;
    }

    @Bean
    @Primary
    public ILabelRepository getLabelRepository() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return this.labelRepositoryJPA;
        }
        else{
            log.warn("Unsupported implementation: "+ implementation);
            throw new IllegalArgumentException("Unsupported implementation: "+implementation);
        }
    }
}
