package com.bug_board.bugboard26.backend.security;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public MyUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null){
            log.error(username + " not found.");
            throw new UsernameNotFoundException(username + " not found.");
        }

        return new UserPrincipal(user);
    }
}
