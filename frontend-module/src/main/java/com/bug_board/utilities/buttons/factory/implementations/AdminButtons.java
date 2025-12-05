package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.interfaces.ButtonFactory;

import java.util.List;

public class AdminButtons implements ButtonFactory {
    @Override
    public List<ButtonDefinition> createButtons() {
        return List.of(
                new ButtonDefinition("View all issues", "VIEW_ISSUES")
        );
    }
}
