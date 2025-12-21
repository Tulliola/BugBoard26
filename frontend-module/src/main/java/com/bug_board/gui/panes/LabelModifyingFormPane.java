package com.bug_board.gui.panes;

import com.bug_board.exceptions.views.TitleNotSpecifiedForLabelException;
import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.utilities.BugBoardLabel;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LabelModifyingFormPane extends LabelFormPane {

    private final BugBoardLabel labelToModify;

    public LabelModifyingFormPane(BugBoardLabel labelToModify,
                                  LabelManagementPC labelPC,
                                  StackPane parentContainer) {
        super(labelPC, parentContainer);
        this.labelToModify = labelToModify;
        labelPC.setModifyingPane(this);

        this.initalize();
        this.setSpecificFeaturesForSpecificImplementation();
    }

    @Override
    protected void setSpecificFeaturesForSpecificImplementation() {
        this.descriptionTextArea.setText(labelToModify.getDescription());
        this.titleTextField.setText(labelToModify.getName());
    }

    @Override
    protected Text createHeaderText() {
        Text headerText = new Text("Modify your label!");
        headerText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        return headerText;
    }

    @Override
    protected BugBoardLabel createSampleLabel() {
        sampleLabel = labelToModify;
        return sampleLabel;
    }

    @Override
    protected void clickConfirmButton() {
        try{
            errorLabel.setManaged(false);
            checkMandatoryFields();
            labelPC.onConfirmModifyingButtonClicked();
        }
        catch(TitleNotSpecifiedForLabelException exc){
            errorLabel.setText(exc.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setManaged(true);
        }
    }

    public Integer getIdLabel() {
        return this.labelToModify.getLabelId();
    }
}
