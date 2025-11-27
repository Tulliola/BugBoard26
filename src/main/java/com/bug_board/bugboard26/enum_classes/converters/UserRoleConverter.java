package com.bug_board.bugboard26.enum_classes.converters;

import com.bug_board.bugboard26.enum_classes.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if(userRole == null)
            return null;

        return userRole.toString();
    }

    @Override
    public UserRole convertToEntityAttribute(String userRoleString) {
        if(userRoleString == null)
            return null;

        for(UserRole userRole : UserRole.values()) {
            if(userRole.toString().equals(userRoleString))
                return userRole;
        }

        throw new IllegalArgumentException("Unknown issue type: " + userRoleString);
    }
}
