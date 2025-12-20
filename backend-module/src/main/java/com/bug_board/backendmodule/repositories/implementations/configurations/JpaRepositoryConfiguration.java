package com.bug_board.backendmodule.repositories.implementations.configurations;

import com.bug_board.backendmodule.repositories.implementations.jpa_implementations.*;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(name = "app.implementation", havingValue = "jpa", matchIfMissing = true)
@EnableJpaRepositories(basePackages = "com.bug_board.backendmodule.repositories.implementations.jpa_implementations")
@EntityScan(basePackages = "com.bug_board.backendmodule.entity")
@Slf4j
public class JpaRepositoryConfiguration {
    @Bean
    public IIssueRepository jpaIssueRepository(IIssueRepositoryJPA repositoryGeneratedBySpring) {
        log.info("Issue Repository: JPA Implementation");
        return new IssueRepositoryJpaAdapter(repositoryGeneratedBySpring);
    }

    @Bean
    public ILabelRepository jpaLabelRepository(ILabelRepositoryJPA repositoryGeneratedBySpring) {
        log.info("Label Repository: JPA Implementation");
        return new LabelRepositoryJpaAdapter(repositoryGeneratedBySpring);
    }

    @Bean
    public IProjectRepository jpaProjectRepository(IProjectRepositoryJPA repositoryGeneratedBySpring) {
        log.info("Project Repository: JPA Implementation");
        return new ProjectRepositoryJpaAdapter(repositoryGeneratedBySpring);
    }

    @Bean
    public IUserRepository jpaUserRepository(IUserRepositoryJPA repositoryGeneratedBySpring) {
        log.info("User Repository: JPA Implementation");
        return new UserRepositoryJpaAdapter(repositoryGeneratedBySpring);
    }
}
