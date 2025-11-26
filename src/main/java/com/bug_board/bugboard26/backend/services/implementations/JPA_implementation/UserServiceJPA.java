package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.entity.factories.UserFactory;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.backend.services.mappers.UserMapper;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import com.bug_board.bugboard26.exception.backend.UserAlreadyExistsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserFactory userFactory;

    public UserServiceJPA(IUserRepository userRepository, PasswordEncoder passwordEncoder, UserFactory userFactory) {
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public UserSummaryDTO registerNewUser(UserCreationDTO user) {
        if(userRepository.existsByUsername(user.getUsername()))
            throw new UserAlreadyExistsException("User with this username already exists.");

        User newUser = userFactory.createUser(user.getRole());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.registerNewUser(newUser);
        return UserMapper.toUserSummaryDTO(createdUser);
    }
}
