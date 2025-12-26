package com.bug_board.backendmodule.services.implementations.configurations;


import com.bug_board.backendmodule.entity.factories.UserFactory;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import com.bug_board.backendmodule.security.JWTService;
import com.bug_board.backendmodule.services.implementations.jpa_implementations.*;
import com.bug_board.backendmodule.services.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConditionalOnProperty(name = "app.implementation", havingValue = "jpa", matchIfMissing = true)
@Slf4j
public class JpaServiceConfiguration {
    @Bean
    public IIssueService jpaIssueService(IIssueRepository issueRepositoryBySpring,
                                         IProjectService projectServiceBySpring,
                                         ILabelService labelServiceBySpring,
                                         IUserService userServiceBySpring) {

        log.info("Issue Service: JPA Implementation");
        return new IssueServiceJPA(issueRepositoryBySpring, projectServiceBySpring, labelServiceBySpring, userServiceBySpring);
    }

    @Bean
    public IAuthenticationService jpaAuthService(JWTService jwtServiceBySpring,
                                                 IUserRepository userRepositoryBySpring,
                                                 AuthenticationManager authenticationManager) {
        log.info("Authentication Service: JPA Implementation");
        return new AuthenticationServiceJPA(jwtServiceBySpring, userRepositoryBySpring, authenticationManager);
    }

    @Bean
    public IEmailService jpaEmailService(JavaMailSender mailSenderBySpring) {
        log.info("Email Service: JavaMailSender Implementation");
        return new EmailServiceJPA(mailSenderBySpring);
    }

    @Bean
    public ILabelService jpaLabelService(ILabelRepository labelRepositoryBySpring,
                                         IUserService userServiceBySpring) {
        log.info("Label Service: JPA Implementation");
        return new LabelServiceJPA(labelRepositoryBySpring, userServiceBySpring);
    }

    @Bean
    public IProjectService jpaProjectService(IProjectRepository projectRepositoryBySpring,
                                             IUserService userServiceBySpring) {
        log.info("Project Service: JPA Implementation");
        return new ProjectServiceJPA(projectRepositoryBySpring, userServiceBySpring);
    }

    @Bean
    public IUserService jpaUserService(IUserRepository userRepositoryBySpring,
                                       PasswordEncoder passwordEncoder,
                                       UserFactory userFactory,
                                       IProjectRepository projectRepositoryBySpring,
                                       IEmailService emailServiceBySpring) {
        log.info("User Service: JPA Implementation");
        return new UserServiceJPA(userRepositoryBySpring, passwordEncoder, userFactory, projectRepositoryBySpring, emailServiceBySpring);
    }
}
