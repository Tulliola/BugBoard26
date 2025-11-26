package com.bug_board.bugboard26.backend.entity.factories;

import com.bug_board.bugboard26.backend.entity.Admin;
import com.bug_board.bugboard26.backend.entity.RegularUser;
import com.bug_board.bugboard26.backend.entity.SuperAdmin;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.enum_classes.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User createUser(UserRole role) {
        if(role.getRole().equals("ROLE_ADMIN"))
            return new Admin();
        else if(role.getRole().equals("ROLE_USER"))
            return new RegularUser();
        else
            return new SuperAdmin();
    }
}
