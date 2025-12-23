package com.bug_board.gui.panes;

import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.exceptions.views.*;
import com.bug_board.presentation_controllers.ReportIssuePC;
import com.bug_board.utilities.BugBoardLabel;
import com.bug_board.utilities.LabelDropdownMenu;
import com.bug_board.utilities.animations.FloatingLabel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ReportIssuePane extends StackPane {
    @Getter
    private final StackPane parentContainer;
    private final ReportIssuePC reportIssuePC;

    private final VBox contentPane = new VBox();
    private final HBox typeIssueBox = new HBox();
    private final Text typeIssueText =  new Text("Which type of issue would you like to report?");

    private final HBox typeIssueRadioButtonBox = new HBox();
    @Getter
    private final ToggleGroup typeIssueToggleGroup = new ToggleGroup();
    @Getter
    private final RadioButton bugRadioButton = new RadioButton();
    @Getter
    private final RadioButton questionRadioButton = new RadioButton();
    @Getter
    private final RadioButton documentationRadioButton = new RadioButton();
    @Getter
    private final RadioButton newFeatureRadioButton = new RadioButton();

    private final HBox issueAttributesBox = new HBox();
    private final VBox titleAndDescriptionBox = new VBox();
    @Getter
    private final TextField titleTextField= new TextField();
    @Getter
    private final TextArea descriptionTextArea = new TextArea();
    private final VBox priorityAndLabelsBox = new VBox();
    @Getter
    private final ComboBox<String> priorityComboBox = new ComboBox<>();
    private LabelDropdownMenu labelDropdownMenu;

    private final ArrayList<byte[]> binaryFiles = new ArrayList<>(NUM_OF_IMAGES);

    private final Label errorLabel = new Label();

    private static final int MAX_TITLE_CHARS = 50;
    private static final int MAX_DESCRIPTION_CHARS = 300;
    private static final int NUM_OF_IMAGES = 3;


    public ReportIssuePane(StackPane parentContainer, ReportIssuePC reportIssuePC) {
        this.parentContainer = parentContainer;
        this.reportIssuePC = reportIssuePC;
        reportIssuePC.setPane(this);

        this.initialize();
    }


    private void initialize() {
        this.setStyle();
        this.setBackground();
        this.getChildren().add(this.setContentPane());
    }

    private VBox setContentPane() {
        contentPane.getChildren().add(setHeaderGif());

        contentPane.getChildren().add(setTypeIssueBox());

        contentPane.getChildren().add(setIssueRadioButtonBox());

        contentPane.getChildren().add(setFillFormBox());

        contentPane.getChildren().add(setAttributesBox());

        contentPane.getChildren().add(new Text("Attach up to three images"));

        contentPane.getChildren().add(setImagesChooserBox());

        contentPane.getChildren().add(setErrorLabel());

        contentPane.getChildren().add(setConfirmButton());

        return contentPane;
    }

    private Node setHeaderGif() {
        ImageView headerGif = new ImageView(new Image(getClass().getResourceAsStream("/gifs/form.gif")));
        headerGif.setFitHeight(100);
        headerGif.setFitWidth(100);

        return headerGif;
    }

    private Node setErrorLabel() {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setManaged(false);

        return errorLabel;
    }

    private Node setConfirmButton() {
        Button confirmButton = new Button("Confirm");
        confirmButton.setAlignment(Pos.CENTER);
        confirmButton.setOnAction(event -> {
            handleConfirmButtonClick();
        });
        return confirmButton;
    }

    private void handleConfirmButtonClick() {
        try {
            reportIssuePC.onConfirmButtonClicked();
        }
        catch (NoTitleSpecifiedForIssueException | NoDescriptionForIssueException | NoTipologySpecifiedException | IssueImageTooLargeException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setManaged(true);
        }
    }

    private Node setImagesChooserBox() {
        List<StackPane> imagesStackPanes = new ArrayList<>(NUM_OF_IMAGES);

        for(int i = 0; i < NUM_OF_IMAGES; i++) {
            StackPane imageStackPane = new StackPane();
            binaryFiles.add(null);
            imagesStackPanes.add(setImagesStackPane(imageStackPane, i));
        }

        HBox addImagesBox = new HBox();
        addImagesBox.setAlignment(Pos.CENTER);
        addImagesBox.setSpacing(30);

        addImagesBox.getChildren().addAll(imagesStackPanes);
        return addImagesBox;
    }

    private StackPane setImagesStackPane(StackPane imageStackPane, int index) {
        Button imageButton = new Button("Choose an image");
        imageButton.getStyleClass().add("file-chooser-button");
        ImageView imageChosen  = new ImageView();

        imageButton.setOnAction(e -> {
           FileChooser fileChooser = setFileChooser();

           setImageChosenProperties(imageChosen, imageButton);

           File choosenFile = fileChooser.showOpenDialog(this.getScene().getWindow());

           if(choosenFile != null){
               addFile(index, choosenFile, imageChosen, imageButton);
           }
           else {
               removeFile(index, imageButton);
           }
        });

        imageStackPane.getChildren().addAll(imageButton, imageChosen);

        return imageStackPane;
    }

    private void removeFile(int index, Button imageButton) {
        imageButton.setVisible(true);
        binaryFiles.set(index, null);
    }

    private void addFile(int index, File choosenFile, ImageView imageChosen, Button imageButton) {
        try {
            binaryFiles.set(index, Files.readAllBytes(choosenFile.toPath()));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Image image = new Image(choosenFile.toURI().toString());
        imageChosen.setImage(image);
        imageChosen.setVisible(true);
        imageButton.setVisible(false);
    }

    private static void setImageChosenProperties(ImageView imageChosen, Button imageButton) {
        imageChosen.setFitHeight(200);
        imageChosen.setFitWidth(200);
        imageChosen.setPreserveRatio(true);
        imageChosen.setVisible(false);

        imageChosen.setOnMouseClicked(event -> {
           imageButton.fire();
        });
    }

    private static FileChooser setFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File initialDirectory = new File(System.getProperty("user.home"));
        if(initialDirectory.exists()){
           fileChooser.setInitialDirectory(initialDirectory);
        }
        return fileChooser;
    }

    private Node setAttributesBox() {
        issueAttributesBox.setAlignment(Pos.CENTER);
        issueAttributesBox.getChildren().add(setTitleAndDescriptionBox());
        issueAttributesBox.getChildren().add(setPriorityAndLabelsBox());

        HBox.setHgrow(titleAndDescriptionBox, Priority.ALWAYS);
        HBox.setHgrow(priorityAndLabelsBox, Priority.ALWAYS);

        return issueAttributesBox;
    }

    private Node setPriorityAndLabelsBox() {
        priorityAndLabelsBox.setSpacing(70);

        priorityAndLabelsBox.setAlignment(Pos.TOP_RIGHT);

        setPriorityComboBox();

        VBox priorityBox = new VBox();
        priorityBox.getChildren().addAll(new Text("Priority"), priorityComboBox);
        priorityAndLabelsBox.getChildren().addAll(priorityBox);

        priorityAndLabelsBox.setPadding(new Insets(0, 0, 0, 50));

        setLabelDropdownMenu();

        VBox labelsBox = new VBox();
        labelsBox.getChildren().addAll(new Text("Labels"), labelDropdownMenu);
        priorityAndLabelsBox.getChildren().addAll(labelsBox);
        return priorityAndLabelsBox;
    }

    private void setLabelDropdownMenu() {
        List<LabelSummaryDTO> usersLabels = reportIssuePC.getUsersLabels();
        List<BugBoardLabel> usersBugBoardLabels = new ArrayList<>();
        for(LabelSummaryDTO usersLabel : usersLabels){
            BugBoardLabel bugBoardLabelFromLabel = new BugBoardLabel(usersLabel);
            bugBoardLabelFromLabel.setToolTipDescription(usersLabel.getDescription());

            usersBugBoardLabels.add(bugBoardLabelFromLabel);
        }

        labelDropdownMenu = new LabelDropdownMenu(usersBugBoardLabels);
        labelDropdownMenu.setPadding(new Insets(15, 0, 0, 0));
    }

    private void setPriorityComboBox() {
        priorityComboBox.setItems(FXCollections.observableArrayList(
                "High",
                "Medium",
                "Low",
                "Don't specify"
        ));

        priorityComboBox.setMinWidth(300);
        priorityComboBox.setMaxWidth(300);
        priorityComboBox.getSelectionModel().select("Don't specify");
    }


    private Node setTitleAndDescriptionBox() {
        setTextFieldProperties(MAX_TITLE_CHARS, titleTextField);

        setTextFieldProperties(MAX_DESCRIPTION_CHARS, descriptionTextArea);

        descriptionTextArea.setWrapText(true);
        descriptionTextArea.getStyleClass().add("no-border-textarea");

        StackPane textFieldWrapper = FloatingLabel.createFloatingLabelField(titleTextField, "Title (max "+MAX_TITLE_CHARS+" characters)");
        VBox.setMargin(textFieldWrapper, new Insets(0, 0, 20, 0));

        StackPane textAreaWrapper = FloatingLabel.createFloatingLabelField(descriptionTextArea, "Description (max "+MAX_DESCRIPTION_CHARS+" characters)");
        VBox.setMargin(textAreaWrapper, new Insets(20, 0, 20, 0));

        titleAndDescriptionBox.getChildren().addAll(textFieldWrapper, textAreaWrapper);
        return titleAndDescriptionBox;
    }

    private void setTextFieldProperties(final int MAX_CHARS, TextInputControl titleTextField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.length() > MAX_CHARS)
                return null;
            return change;
        };

        titleTextField.setTextFormatter(new TextFormatter<>(filter));
    }

    private Node setFillFormBox() {
        HBox fillFormTextBox = new HBox();
        fillFormTextBox.setAlignment(Pos.CENTER);

        Text fillFormText = new Text("Fill out the following form to submit your report");
        fillFormText.setStyle("-fx-font-size: 24px");

        fillFormTextBox.getChildren().add(fillFormText);
        fillFormTextBox.setPadding(new Insets(20));

        return fillFormTextBox;
    }

    private Node setTypeIssueBox() {
        typeIssueText.setStyle("-fx-font-size: 24px");
        typeIssueText.setTextAlignment(TextAlignment.CENTER);
        typeIssueBox.getChildren().add(typeIssueText);
        typeIssueBox.setAlignment(Pos.CENTER);

        return typeIssueBox;
    }

    private Node setIssueRadioButtonBox(){
        typeIssueRadioButtonBox.setAlignment(Pos.CENTER);
        typeIssueRadioButtonBox.setSpacing(15);
        typeIssueRadioButtonBox.setPadding(new Insets(20));
        typeIssueRadioButtonBox.setStyle("-fx-border-color: white white -color-primary white");

        setRadioButtons();

        return typeIssueRadioButtonBox;
    }

    private void setRadioButtons() {
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                getBugRadioButton(),
                new Image(new ByteArrayInputStream(IssueTipology.BUG.getAssociatedImage())),
                "Report malfunctioning"));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                questionRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.QUESTION.getAssociatedImage())),
                "Request for clarity"
        ));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                documentationRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.DOCUMENTATION.getAssociatedImage())),
                "Report issues regarding documentation"
        ));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                newFeatureRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.NEW_FEATURE.getAssociatedImage())),
                "Request or suggest new feature"
        ));
    }

    public RadioButton setRadioButton(RadioButton radioButtonToSet, Image radioButtonImage, String tooltipString){
        radioButtonToSet.setToggleGroup(typeIssueToggleGroup);
        radioButtonToSet.getStyleClass().add("icon-radio");

        ImageView imageView = new ImageView(radioButtonImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setTranslateX(-4);
        Tooltip tooltip = new Tooltip(tooltipString);
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(radioButtonToSet, tooltip);
        radioButtonToSet.setGraphic(imageView);

        return radioButtonToSet;
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");
        this.setOnMouseClicked(event -> {
            reportIssuePC.closePane(event);
        });
    }

    private void setStyle() {
        contentPane.getStyleClass().add("overlay-pane");
        contentPane.setStyle("-fx-max-height: 1000px; -fx-min-height: 1000px; -fx-min-width: 1200px; -fx-max-width: 1200px");
    }

    public List<Integer> getChoosenLabels() {
        return labelDropdownMenu.getSelectedLabels();
    }

    public List<byte[]> getIssueImages(){
        List<byte[]> images = new ArrayList<>();

        for(byte[] image: binaryFiles)
            if(image != null)
                images.add(image);
        return images;
    }
}