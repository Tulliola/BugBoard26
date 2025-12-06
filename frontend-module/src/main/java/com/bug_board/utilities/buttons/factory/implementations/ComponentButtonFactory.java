package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.factory.interfaces.ButtonFactory;

public class ComponentButtonFactory {

    public static ButtonFactory getProjectCardButtonsByRole(String role) {
        return switch (role) {
            case "ROLE_ADMIN", "ROLE_SUPERADMIN" -> new AdminButtons();
            case "ROLE_USER" -> new RegularUserButtons();
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
