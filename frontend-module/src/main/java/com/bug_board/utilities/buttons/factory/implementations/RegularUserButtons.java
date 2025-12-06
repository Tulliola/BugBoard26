package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.interfaces.ButtonFactory;

import java.util.ArrayList;
import java.util.List;

public class RegularUserButtons extends AdminButtons implements ButtonFactory {
    @Override
    public List<ButtonDefinition> createProjectCardButtons() {
        List<ButtonDefinition> sameButtonsAsAdmin = super.createButtons();
        List<ButtonDefinition> regularUserButtons = new ArrayList<>(sameButtonsAsAdmin);

        regularUserButtons.add(new ButtonDefinition("Report a new issue", "REPORT_ISSUE"));

        return regularUserButtons;
    }
}
