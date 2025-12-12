package com.bug_board.gui.panes;

import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.presentation_controllers.ReportIssuePC;
import com.bug_board.utilities.BugBoardLabel;
import com.bug_board.utilities.DropdownMenu;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ReportIssuePane extends StackPane {
    private StackPane parentContainer;
    private final ReportIssuePC reportIssuePC;

    private VBox contentPane = new VBox();

    private HBox typeIssueBox = new HBox();
    private Text typeIssueText =  new Text("Which type of issue would you like to report?");

    private HBox typeIssueRadioButtonBox = new HBox();
    private ToggleGroup typeIssueToggleGroup = new ToggleGroup();
    private RadioButton bugRadioButton = new RadioButton("Bug");
    private RadioButton questionRadioButton = new RadioButton("Question");
    private RadioButton documentationRadioButton = new RadioButton("Documentation");
    private RadioButton newFeatureRadioButton = new RadioButton("New Feature");

    private HBox fillFormTextBox = new HBox();
    private Text fillFormText = new Text("Fill out the following form to submit your report");

    private HBox issueAttributesBox = new HBox();
    private VBox titleAndDescriptionBox = new VBox();
    private TextField titleTextField= new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private VBox priorityAndLabelsBox = new VBox();
    private ComboBox<String> priorityComboBox = new ComboBox<>();

    private Text addImagesText = new  Text("Attach up to three images");
    private HBox addImagesBox = new HBox();
    private List<StackPane> imagesStackPane = new ArrayList<>(NUM_OF_IMAGES);
    private List<File> filesChoosen = new ArrayList<>(NUM_OF_IMAGES);
    private byte[][] binaryFiles = new byte[NUM_OF_IMAGES][];

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
        this.getChildren().add(setContentPane());
    }

    private VBox setContentPane() {
        contentPane.getChildren().add(setTypeIssueBox());


        bugRadioButton.setToggleGroup(typeIssueToggleGroup);
        questionRadioButton.setToggleGroup(typeIssueToggleGroup);
        documentationRadioButton.setToggleGroup(typeIssueToggleGroup);
        newFeatureRadioButton.setToggleGroup(typeIssueToggleGroup);

        bugRadioButton.getStyleClass().add("icon-radio");
        questionRadioButton.getStyleClass().add("icon-radio");
        documentationRadioButton.getStyleClass().add("icon-radio");
        newFeatureRadioButton.getStyleClass().add("icon-radio");

        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                bugRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.Bug.getAssociatedImage())),
                "Report malfunctioning"));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                questionRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.Question.getAssociatedImage())),
                "Request for clarity"
        ));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                documentationRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.Documentation.getAssociatedImage())),
                "Report issues regarding documentation"
        ));
        typeIssueRadioButtonBox.getChildren().add(setRadioButton(
                newFeatureRadioButton,
                new Image(new ByteArrayInputStream(IssueTipology.NewFeature.getAssociatedImage())),
                "Request or suggest new feature"
        ));

        contentPane.getChildren().add(setIssueRadioButtonBox());

        contentPane.getChildren().add(setFillFormBox());

        contentPane.getChildren().add(setAttributesBox());

        contentPane.getChildren().add(addImagesText);

        contentPane.getChildren().add(setImagesChooserBox());

        return contentPane;
    }

    private Node setImagesChooserBox() {
        for(int i = 0; i < NUM_OF_IMAGES; i++) {
            StackPane imageStackPane = new StackPane();
            imagesStackPane.add(setImagesStackPane(imageStackPane, i));
        }

        addImagesBox.setAlignment(Pos.CENTER);
        addImagesBox.setSpacing(30);

        addImagesBox.getChildren().addAll(imagesStackPane);
        return addImagesBox;
    }

    private StackPane setImagesStackPane(StackPane imageStackPane, int index) {
        Button imageButton = new Button("Choose an image");
        imageButton.getStyleClass().add("file-chooser-button");
        ImageView imageChoosen  = new ImageView();

        imageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

            File initialDirectory = new File(System.getProperty("user.home"));
            if(initialDirectory.exists()){
               fileChooser.setInitialDirectory(initialDirectory);
           }

           imageChoosen.setFitHeight(200);
           imageChoosen.setFitWidth(200);
           imageChoosen.setPreserveRatio(true);
           imageChoosen.setVisible(false);
           imageChoosen.setOnMouseClicked(event -> {
              imageButton.fire();
           });

           File choosenFile = fileChooser.showOpenDialog(this.getScene().getWindow());

           if(choosenFile != null){
               try {
                   binaryFiles[index] = Files.readAllBytes(choosenFile.toPath());
               } catch (IOException ex) {
                   throw new RuntimeException(ex);
               }
               Image image = new Image(choosenFile.toURI().toString());
               imageChoosen.setImage(image);
               imageChoosen.setVisible(true);
               imageButton.setVisible(false);
           }
           else {
               imageButton.setVisible(true);
               binaryFiles[index] = null;
           }
        });

        imageStackPane.getChildren().addAll(imageButton, imageChoosen);

        return imageStackPane;
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
        String highPriority = new String("High");
        String mediumPriority = new String("Medium");
        String lowPriority = new String("Low");
        String noPriority = new String("Don't specify");

        priorityComboBox.setItems(FXCollections.observableArrayList(
                highPriority,
                mediumPriority,
                lowPriority,
                noPriority
        ));

        priorityComboBox.setMinWidth(300);
        priorityComboBox.setMaxWidth(300);
        priorityComboBox.getSelectionModel().select("Don't specify");

        VBox priorityBox = new VBox();
        priorityBox.getChildren().addAll(new Text("Priority"), priorityComboBox);
        priorityAndLabelsBox.getChildren().addAll(priorityBox);

        priorityAndLabelsBox.setPadding(new Insets(0, 0, 0, 50));

        List<LabelSummaryDTO> usersLabels = reportIssuePC.getUsersLabels();
        List<BugBoardLabel> usersBugBoardLabels = new ArrayList<>();
        for(LabelSummaryDTO usersLabel : usersLabels){
            usersBugBoardLabels.add(new BugBoardLabel(usersLabel.getName(), usersLabel.getColor()));
        }

        DropdownMenu dropdownMenu = new DropdownMenu(usersBugBoardLabels);
        dropdownMenu.setPadding(new Insets(15, 0, 0, 0));

        VBox labelsBox = new VBox();
        labelsBox.getChildren().addAll(new Text("Labels"), dropdownMenu);
        priorityAndLabelsBox.getChildren().addAll(labelsBox);
        return priorityAndLabelsBox;
    }


    private Node setTitleAndDescriptionBox() {
        UnaryOperator<TextFormatter.Change> titleFilter = change -> {
            String newText = change.getControlNewText();

            if (newText.length() > MAX_TITLE_CHARS)
                return null;
            return change;
        };

        titleTextField.setTextFormatter(new TextFormatter<>(titleFilter));

        descriptionTextArea.setWrapText(true);
        descriptionTextArea.getStyleClass().add("no-border-textarea");
        UnaryOperator<TextFormatter.Change> descriptionFilter = change -> {
            String newText = change.getControlNewText();

            if (newText.length() > MAX_DESCRIPTION_CHARS)
                return null;
            return change;
        };

        descriptionTextArea.setTextFormatter(new TextFormatter<>(descriptionFilter));

        StackPane textFieldWrapper = FloatingLabel.createFloatingLabelField(titleTextField, "Title (max "+MAX_TITLE_CHARS+" characters)");
        VBox.setMargin(textFieldWrapper, new Insets(0, 0, 20, 0));

        StackPane textAreaWrapper = FloatingLabel.createFloatingLabelField(descriptionTextArea, "Description (max "+MAX_DESCRIPTION_CHARS+" characters)");
        VBox.setMargin(textAreaWrapper, new Insets(20, 0, 20, 0));

        titleAndDescriptionBox.getChildren().addAll(textFieldWrapper, textAreaWrapper);
        return titleAndDescriptionBox;
    }

    private Node setFillFormBox() {
        fillFormTextBox.setAlignment(Pos.CENTER);
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

        return typeIssueRadioButtonBox;
    }

    public RadioButton setRadioButton(RadioButton radioButtonToSet, Image radioButtonImage, String tooltipString){
        ImageView imageView = new ImageView(radioButtonImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setTranslateX(-4);
        Tooltip tooltip = new Tooltip(tooltipString);
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.install(radioButtonToSet, tooltip);
        radioButtonToSet.setGraphic(imageView);

        return radioButtonToSet;
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.6);");
        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                close();
        });
    }

    private void setStyle() {
        contentPane.getStyleClass().add("overlay-pane");
        contentPane.setStyle("-fx-max-height: 1000px; -fx-min-height: 1000px; -fx-min-width: 1000px; -fx-max-width: 1000px");
    }

    public void close() {
        parentContainer.getChildren().remove(this);
    }
}