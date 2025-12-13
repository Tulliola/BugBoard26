package com.bug_board.backendmodule.mappers;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserMapper() {

    }

    public static UserSummaryDTO toUserSummaryDTO(User userToMap) {
        UserSummaryDTO mappedUser = new UserSummaryDTO();

        mappedUser.setUsername(userToMap.getUsername());
        mappedUser.setImage(userToMap.getBioPic());

        return mappedUser;
    }
}
