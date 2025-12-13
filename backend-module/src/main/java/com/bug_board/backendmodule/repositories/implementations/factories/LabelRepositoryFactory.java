package com.bug_board.backendmodule.repositories.implementations.factories;

import com.bug_board.backendmodule.repositories.implementations.jpa_implementations.LabelRepositoryJpaAdapter;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
