package com.bug_board.utilities.buttons.factory.implementations;

import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.interfaces.IButtonsProvider;
import java.util.List;

public class RegularUserButtons extends AdminButtons implements IButtonsProvider {
    @Override
    public List<ButtonDefinition> createProjectCardButtons() {
        return List.of(
                new ButtonDefinition("Report a new issue", "REPORT_ISSUE"),
                new ButtonDefinition("View all issues", "VIEW_ISSUES")
        );
    }

    @Override
    public List<ButtonDefinition> createTitleBarButtons() {
        return List.of(
                new ButtonDefinition("Issues reported by me", "VIEW_PERSONAL_ISSUES"),
                new ButtonDefinition("Create a new label", "CREATE_LABEL"),
                new ButtonDefinition("View my labels", "VIEW_LABELS")
        );
    }
}
