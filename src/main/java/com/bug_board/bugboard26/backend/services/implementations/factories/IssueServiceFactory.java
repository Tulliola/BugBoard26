package com.bug_board.bugboard26.backend.services.implementations.factories;

import com.bug_board.bugboard26.backend.services.implementations.JPA_implementation.IssueServiceJPA;
import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class IssueServiceFactory {
    @Value("${app.issue-service.implementation}")
    private String implementation;

    private IssueServiceJPA issueServiceJPA;

    public IssueServiceFactory(IssueServiceJPA issueServiceJPA) {
        this.issueServiceJPA = issueServiceJPA;
    }

    @Bean
    public IIssueService getIssueService() {
        if (implementation.equalsIgnoreCase("jpa")) {
            log.info("JPA implementation");
            return this.issueServiceJPA;
        }
        else{
            log.warn("Unsupported implementation: "+implementation);
            throw new IllegalArgumentException("Unsupported implementation: " + implementation);
        }
    }
}
