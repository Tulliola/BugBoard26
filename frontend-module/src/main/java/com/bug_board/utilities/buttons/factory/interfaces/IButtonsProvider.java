package com.bug_board.utilities.buttons.factory.interfaces;

import com.bug_board.utilities.buttons.ButtonDefinition;

import java.util.List;

public interface IButtonsProvider {
    public List<ButtonDefinition> createProjectCardButtons();
    public List<ButtonDefinition> createTitleBarButtons();
}
