package com.bug_board.bugboard26.enum_classes;

public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SUPERADMIN("ROLE_SUPERADMIN");

    private final String role;

    UserRole(String roleTipology){
        this.role = roleTipology;
    }

    public String getRoleName() {
        return role;
    }
}
