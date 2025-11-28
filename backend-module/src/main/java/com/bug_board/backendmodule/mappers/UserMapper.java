package com.bug_board.backendmodule.mappers;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;

public class UserMapper {

    public static UserSummaryDTO toUserSummaryDTO(User userToMap) {
        UserSummaryDTO mappedUser = new UserSummaryDTO();

        mappedUser.setUsername(userToMap.getUsername());
        mappedUser.setImage(userToMap.getBioPic());

        return mappedUser;
    }

    public static void updateEntityFromDTO(UserCreationDTO userDTO, User userToUpdate) {
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPassword(userDTO.getPassword());
        userToUpdate.setRole(userDTO.getRole());
        userToUpdate.setBioPic(userDTO.getBioPic());
    }
}
