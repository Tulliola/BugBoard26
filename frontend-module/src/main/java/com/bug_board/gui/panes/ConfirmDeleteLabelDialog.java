package com.bug_board.gui.panes;

import com.bug_board.utilities.BugBoardLabel;
import javafx.scene.layout.VBox;

public class ConfirmDeleteLabelDialog extends VBox {
    private final BugBoardLabel labelToEventuallyDelete;

    public ConfirmDeleteLabelDialog(BugBoardLabel labelToEventuallyDelete) {
        this.labelToEventuallyDelete = labelToEventuallyDelete;

        initialize();
    }

    private void initialize() {

    }
}
