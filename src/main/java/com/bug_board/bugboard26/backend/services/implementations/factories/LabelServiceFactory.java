package com.bug_board.bugboard26.backend.services.implementations.factories;

import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.LabelServiceJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class LabelServiceFactory {
    @Value("${app.label-service.implementation}")
    private String implementation;

    private final LabelServiceJPA labelServiceJPA;

    public LabelServiceFactory(LabelServiceJPA labelServiceJPA) {
        this.labelServiceJPA = labelServiceJPA;
    }

    @Bean
    @Primary
    public LabelServiceJPA labelServiceJPA() {
        if(implementation.equalsIgnoreCase("jpa")){
            log.info("JPA implementation");
            return labelServiceJPA;
        }
        else{
            log.warn("Unsupported implementation: "+implementation);
            throw new IllegalArgumentException("Unsupported implementation: " + implementation);
        }
    }
}
