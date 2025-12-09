package com.bug_board.backendmodule.services.implementations.JPA_implementation;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.entity.factories.UserFactory;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import com.bug_board.backendmodule.services.interfaces.IEmailService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.backendmodule.mappers.UserMapper;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.backendmodule.exception.backend.ResourceAlreadyExistsException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.dto.email.EmailToSendDTOFactory;
import com.bug_board.dto.email.IEmailToSendDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bug_board.backendmodule.util.PasswordGenerator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceJPA implements IUserService {
    private final IProjectRepository projectRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserFactory userFactory;
    private final IEmailService emailService;

    public UserServiceJPA(IUserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          UserFactory userFactory,
                          IProjectRepository projectRepository, IEmailService emailService) {
        this.projectRepository = projectRepository;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    @Transactional
    public UserSummaryDTO registerNewUser(UserCreationDTO user) {
        if(userRepository.existsByEmailAndRole(user.getEmail(), user.getRole()))
            throw new ResourceAlreadyExistsException("User with this email and role already exists.");

        String generatedPassword = PasswordGenerator.generatePassword(16);

        User newUser = userFactory.createUser(user.getRole());
        newUser.setUsername(this.assignUsernameAutomatically(user.getEmail(), user.getRole().getRoleName()));
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(generatedPassword));

        IEmailToSendDTO emailToSendDTO = EmailToSendDTOFactory.getInstance().getEmailDTO();
        emailToSendDTO.setAddressee(user.getEmail());
        emailToSendDTO.setSubject("Your BugBoard26 Credentials");
        emailToSendDTO.setBody("Your BugBoard26 Credentials are:\nUsername: "+ newUser.getUsername()+"\nPassword: "+generatedPassword);

        User createdUser = userRepository.registerNewUser(newUser);
        emailService.sendEmail(emailToSendDTO);

        return UserMapper.toUserSummaryDTO(createdUser);
    }

    @Override
    public User getUser(String username) {
        User retrievedUser = userRepository.findUserByUsername(username);

        if(retrievedUser == null)
            throw new ResourceNotFoundException("User doesn't exist.");
        else
            return retrievedUser;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject) {
        List<User> currentCollaborators = projectRepository.getProjectMembers(idProject);

        Set<String> currentCollaboratorsUsernames = currentCollaborators
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());

        List<User> addableUser = userRepository.findAllUsers()
                .stream()
                .filter(user -> !currentCollaboratorsUsernames.contains(user.getUsername()))
                .toList();

        return addableUser.stream().map(UserMapper::toUserSummaryDTO).collect(Collectors.toList());
    }

    @Override
    public User findUserByUsername(String collaboratorUsername) {
        return userRepository.findUserByUsername(collaboratorUsername);
    }

    private String assignUsernameAutomatically(String emailToParse, String role){
        String username;

        username = emailToParse.substring(0, emailToParse.indexOf("@"));
        username += "_" + role.substring(role.indexOf("_")+1).toLowerCase();

        return username;
    }
}
