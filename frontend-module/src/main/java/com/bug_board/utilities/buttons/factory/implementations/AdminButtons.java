package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.interfaces.IButtonsProvider;

import java.util.List;

public class AdminButtons implements IButtonsProvider {
    @Override
    public List<ButtonDefinition> createProjectCardButtons() {
        return List.of(
                new ButtonDefinition("View all issues", "VIEW_ISSUES")
        );
    }

    @Override
    public List<ButtonDefinition> createTitleBarButtons() {
        return List.of(
                new ButtonDefinition("Register a new collaborator", "REGISTER_COLLABORATOR")
        );
    }
}
