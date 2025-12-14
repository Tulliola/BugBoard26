package com.bug_board.gui.panes;

import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.exceptions.views.TitleNotSpecifiedForLabelException;
import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.utilities.BugBoardLabel;
import com.bug_board.utilities.PaletteButton;
import com.bug_board.utilities.animations.FloatingLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;

public class LabelCreationFormPane extends StackPane {
    private final LabelManagementPC labelPC;

    private VBox form;
    private final StackPane parentContainer;
    private BugBoardLabel sampleLabel;
    private Label errorLabel = new Label();
    private TextField titleTextField;
    private TextArea descriptionTextArea;
    private final String[] colorPalette = {"#FF0000", "#FF8000", "#FFFF00",
            "#80FF00", "#00FF00", "#00FF80", "#00FFFF", "#0080FF"};
    private static final int MAX_TITLE_CHARS = 35;
    private static final int MAX_DESCRIPTION_CHARS = 200;

    public LabelCreationFormPane(LabelManagementPC labelPC, StackPane parentContainer) {
        this.parentContainer = parentContainer;
        this.labelPC = labelPC;
        labelPC.setPane(this);

        this.initalize();
    }

    private void initalize() {
        this.setBackground();

        errorLabel.setManaged(false);

        this.getChildren().add(this.createCreationForm());
    }

    private VBox createCreationForm() {
        form = new VBox();
        form.setAlignment(Pos.TOP_CENTER);
        form.setId("form");

        ImageView painterGif = new ImageView(new Image(getClass().getResourceAsStream("/gifs/painter.gif")));
        painterGif.setFitHeight(100);
        painterGif.setFitWidth(100);

        form.getChildren().add(painterGif);

        Text createYourLabelText = new Text("Create your own label!");
        createYourLabelText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        sampleLabel = new BugBoardLabel("This is a Label", "#FFFFFF");


        form.getChildren().addAll(
                createYourLabelText,
                sampleLabel,
                this.createPalette(),
                this.createTitleTextField(),
                this.createDescriptionTextArea(),
                errorLabel,
                this.createConfirmButton()
        );

        return form;
    }

    private StackPane createTitleTextField() {
        titleTextField = new TextField();

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.length() > MAX_TITLE_CHARS)
                return null;
            return change;
        };

        titleTextField.setTextFormatter(new TextFormatter<>(filter));

        sampleLabel.getTextField().textProperty().bind(titleTextField.textProperty());

        StackPane wrapper = FloatingLabel.createFloatingLabelField(titleTextField, "Title (max " + MAX_TITLE_CHARS + " characters)");
        VBox.setMargin(wrapper, new Insets(20, 0, 20, 0));

        return wrapper;
    }

    private StackPane createDescriptionTextArea() {
        descriptionTextArea = new TextArea();

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.length() > MAX_DESCRIPTION_CHARS)
                return null;
            return change;
        };

        descriptionTextArea.setTextFormatter(new TextFormatter<>(filter));

        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setPrefRowCount(4);
        descriptionTextArea.getStyleClass().add("no-border-textarea");

        StackPane wrapper = FloatingLabel.createFloatingLabelField(descriptionTextArea, "Description (max " + MAX_DESCRIPTION_CHARS + " characters)");
        VBox.setMargin(wrapper, new Insets(20, 0, 20, 0));
        return wrapper;
    }

    private HBox createPalette() {
        HBox palette = new HBox();
        palette.setAlignment(Pos.CENTER);
        palette.setSpacing(10);
        VBox.setMargin(palette, new Insets(10, 10, 10, 10));

        for(String color : colorPalette) {
            PaletteButton paletteButton = new PaletteButton(color);

            paletteButton.setOnMouseClicked(event -> {
                sampleLabel.setColor(paletteButton.getColor());
            });

            palette.getChildren().add(paletteButton);
        }

        return palette;
    }

    private Button createConfirmButton() {
        Button confirmButton = new Button();

        Image staticConfirmImage = new Image(getClass().getResource("/icons/confirm_static.png").toExternalForm());
        Image gifConfirmImage = new Image(getClass().getResource("/gifs/confirm.gif").toExternalForm());

        ImageView confirmImageView = new ImageView(staticConfirmImage);
        confirmImageView.setFitHeight(50);
        confirmImageView.setFitWidth(50);

        confirmButton.setGraphic(confirmImageView);

        confirmButton.setOnMouseEntered(event -> {
            confirmImageView.setImage(gifConfirmImage);
            confirmButton.setGraphic(confirmImageView);
        });

        confirmButton.setOnMouseExited(event -> {
            confirmImageView.setImage(staticConfirmImage);
            confirmButton.setGraphic(confirmImageView);
        });

        confirmButton.setOnMouseClicked(event -> {
            this.clickConfirmCreationButton();
        });

        return confirmButton;
    }

    private void clickConfirmCreationButton() {
        try{
            errorLabel.setManaged(false);
            checkMandatoryFields();
            labelPC.onConfirmCreationButtonClicked();
        }
        catch(TitleNotSpecifiedForLabelException | LabelCreationException exc){
            errorLabel.setText(exc.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setManaged(true);
        }
    }

    private void checkMandatoryFields() {
        if(titleTextField.getText().isEmpty())
            throw new TitleNotSpecifiedForLabelException("You must specify at least a title for your label");
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                close();
        });
    }

    public void close() {
        parentContainer.getChildren().remove(this);
    }
    
    public String getChosenColor() {
        return this.sampleLabel.getColor();
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public TextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public VBox getForm() {
        return form;
    }
}
