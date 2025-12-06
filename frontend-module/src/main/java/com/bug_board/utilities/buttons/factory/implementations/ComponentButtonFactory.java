package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.factory.interfaces.IButtonsProvider;

public class ComponentButtonFactory {

    public static IButtonsProvider getButtonFactoryByRole(String role) {
        return switch (role) {
            case "ROLE_ADMIN", "ROLE_SUPERADMIN" -> new AdminButtons();
            case "ROLE_USER" -> new RegularUserButtons();
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
