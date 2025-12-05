package com.bug_board.utilities.buttons;

public class ButtonDefinition {
    private final String text;
    private final String actionId;

    public ButtonDefinition(String text, String actionId) {
        this.text = text;
        this.actionId = actionId;
    }

    public String getText() { return text; }
    public String getActionId() { return actionId; }
}
