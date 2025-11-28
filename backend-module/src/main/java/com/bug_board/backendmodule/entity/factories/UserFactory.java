package com.bug_board.backendmodule.entity.factories;

import com.bug_board.backendmodule.entity.Admin;
import com.bug_board.backendmodule.entity.RegularUser;
import com.bug_board.backendmodule.entity.SuperAdmin;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.enum_classes.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User createUser(UserRole role) {
        if(role.getRoleName().equals("ROLE_ADMIN"))
            return new Admin();
        else if(role.getRoleName().equals("ROLE_USER"))
            return new RegularUser();
        else
            return new SuperAdmin();
    }
}
