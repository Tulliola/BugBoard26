package com.bug_board.gui.views;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.presentation_controllers.IssueVisualizationPC;
import com.bug_board.utilities.IssueSummaryCard;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class IssueVisualizationView extends MyStage {

    private IssueVisualizationPC issuePC;

    private final VBox root = new VBox();
    private final Scene scene = new Scene(root);
    private TitleBar titleBar;
    private StackPane containerUnderTitleBar;
    private BorderPane centralPane;
    private Pagination pagination = new Pagination()    ;
    private String headingText;
    private Integer idProject;

    private List<RadioButton> tipologyFilterRadioButtons = new ArrayList<>();
    private List<RadioButton> priorityFilterRadioButtons = new ArrayList<>();
    private List<RadioButton> stateFilterRadioButtons = new ArrayList<>();
    private VBox noIssuesFound;

    public IssueVisualizationView(IssueVisualizationPC issuePC, String headingText) {
        this.issuePC = issuePC;
        this.headingText = headingText;

        issuePC.setView(this);

        this.initialize();
    }

    public IssueVisualizationView(IssueVisualizationPC issuePC, String headingText, Integer idProject) {
        this(issuePC, headingText);

        this.idProject = idProject;
    }

    private void initialize() {
        this.setFullScreen();
        this.setScene();
    }

    private void setScene() {
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());

        root.setAlignment(Pos.TOP_CENTER);

        setContainerUnderTitleBar();

        titleBar = new TitleBar(this, 80);
        this.setGoBackButton();

        root.getChildren().addAll(
                titleBar,
                this.containerUnderTitleBar
        );

        this.setScene(scene);
        this.setMaximized(true);
    }

    private void setGoBackButton() {
        Button goBackBtn = new Button();
        goBackBtn.setStyle("-fx-background-color: transparent");

        Image goBackStatic = new Image(getClass().getResourceAsStream("/images/goBackStatic.png"));
        Image goBackGif = new Image(getClass().getResourceAsStream("/gifs/goBack.gif"));

        ImageView imageView = new ImageView(goBackStatic);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);


        goBackBtn.setOnMouseClicked(mouseEvent -> {
            this.issuePC.onGoBackButtonClicked();
        });

        goBackBtn.setOnMouseEntered(mouseEvent -> {
            imageView.setImage(goBackGif);
        });

        goBackBtn.setOnMouseExited(mouseEvent -> {
            imageView.setImage(goBackStatic);
        });

        goBackBtn.setGraphic(imageView);

        titleBar.addGoBackButton(goBackBtn);
    }

    private void setContainerUnderTitleBar() {
        containerUnderTitleBar = new StackPane();
        VBox.setVgrow(containerUnderTitleBar, Priority.ALWAYS);

        centralPane = new BorderPane();
        centralPane.setPadding(new Insets(10, 0, 0, 0));

        centralPane.setLeft(this.createSideFilterBar());
        centralPane.setCenter(this.createCentralPane());
        centralPane.setRight(this.createLateralPane());

        containerUnderTitleBar.getChildren().add(centralPane);
    }

    private VBox createCentralPane() {
        VBox centralPane = new VBox();
        centralPane.setAlignment(Pos.TOP_CENTER);
        centralPane.setStyle("-fx-background-color: white; -fx-spacing: 20px");

        Label heading = new Label(this.headingText);
        heading.setStyle("-fx-font-size: 30px");
        heading.setPadding(new Insets(10, 10, 10, 10));
        heading.setWrapText(true);

        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);

        centralPane.getChildren().addAll(
                heading,
                createNoIssuesFoundLabel(),
                this.createPaginationNumbersContainer(),
                spacer2
        );

        return centralPane;
    }

    private Pagination createPaginationNumbersContainer() {
        int numberOfPages = this.issuePC.getNumberOfPages();

        this.setPaginationButtons(numberOfPages);

        if(numberOfPages == 0){
            pagination.setManaged(false);
            noIssuesFound.setManaged(true);
            noIssuesFound.setVisible(true);
        }

        return pagination;
    }

    private VBox createLateralPane() {
        VBox lateralPane = new VBox();
        lateralPane.setPrefWidth(200);
        lateralPane.setMinWidth(200);
        lateralPane.setMaxWidth(200);
        HBox.setHgrow(lateralPane, Priority.ALWAYS);

        lateralPane.setId("background-gradient");

        return lateralPane;
    }

    private Node createNoIssuesFoundLabel() {
        noIssuesFound = new VBox();
        noIssuesFound.setManaged(false);
        noIssuesFound.setVisible(false);
        noIssuesFound.setAlignment(Pos.CENTER);

        Label noIssuesFoundLabel = new Label("No issues found");
        noIssuesFoundLabel.setStyle("-fx-font-style: italic; -fx-font-size: 20px;");

        ImageView noIssuesFoundImages = new ImageView(new Image(getClass().getResourceAsStream("/icons/not_found.png")));

        noIssuesFound.getChildren().addAll(noIssuesFoundImages, noIssuesFoundLabel);

        return noIssuesFound;
    }

    private VBox createSideFilterBar() {
        VBox filterBar = new VBox();
        filterBar.setStyle("-fx-background-color: linear-gradient(to bottom, -color-primary, white)");
        filterBar.setPrefWidth(200);
        filterBar.setMinWidth(200);
        filterBar.setMaxWidth(200);

        Label filterLabel = new Label("Filter by");
        filterLabel.setPadding(new Insets(10, 10, 10, 10));
        filterLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: -color-primary");
        filterLabel.setPrefWidth(filterBar.getPrefWidth());

        ImageView filterPic = new ImageView(new Image(getClass().getResourceAsStream("/icons/filter.png")));
        filterPic.setFitWidth(50);
        filterPic.setFitHeight(50);
        filterLabel.setGraphic(filterPic);


        filterBar.getChildren().addAll(
                filterLabel,
                this.createTipologyFilterBox(),
                this.createPriorityFilterBox(),
                this.createStateFilterBox(),
                this.createFilterButtonBox()
        );

        return filterBar;
    }

    private VBox createTipologyFilterBox() {
        VBox tipologyFilterBar = new VBox();
        HBox titleAndClearButtonBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleAndClearButtonBox.getChildren().addAll(
                this.createFilterLabel("Tipology"),
                spacer,
                this.createClearFiltersButton(this.tipologyFilterRadioButtons)
        );
        tipologyFilterBar.getChildren().add(titleAndClearButtonBox);

        List<IssueTipology> tipologies = issuePC.getTipologies();

        for(IssueTipology tipology: tipologies)
            tipologyFilterBar.getChildren().add(this.createTipologyRadioButton(tipology.toString(), tipology.getAssociatedImage()));

        return tipologyFilterBar;
    }

    private VBox createPriorityFilterBox() {
        VBox priorityFilterBar = new VBox();
        HBox titleAndClearButtonBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleAndClearButtonBox.getChildren().addAll(
                this.createFilterLabel("Priority"),
                spacer,
                this.createClearFiltersButton(this.priorityFilterRadioButtons)
        );
        priorityFilterBar.getChildren().add(titleAndClearButtonBox);

        List<IssuePriority> priorities = issuePC.getPriorities();

        for(IssuePriority priority: priorities)
            priorityFilterBar.getChildren().add(this.createPriorityRadioButton(priority.toString()));

        return priorityFilterBar;
    }

    private VBox createStateFilterBox() {
        VBox stateFilterBox = new VBox();
        HBox titleAndClearButtonBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleAndClearButtonBox.getChildren().addAll(
                this.createFilterLabel("State"),
                spacer,
                this.createClearFiltersButton(this.stateFilterRadioButtons)
        );
        stateFilterBox.getChildren().add(titleAndClearButtonBox);

        List<IssueState> states = issuePC.getStates();

        for(IssueState state: states)
            stateFilterBox.getChildren().add(this.createStateRadioButton(state.toString()));

        return stateFilterBox;
    }

    private Label createFilterLabel(String labelText) {
        Label filterLabel = new Label(labelText);
        filterLabel.setPadding(new Insets(10, 10, 10, 10));
        filterLabel.setStyle("-fx-font-size: 16px");

        return filterLabel;
    }

    private RadioButton createTipologyRadioButton(String text, byte[] image) {
        RadioButton radioButton = this.createRadioButton(text, image);

        this.tipologyFilterRadioButtons.add(radioButton);

        radioButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if(isSelected)
                issuePC.addTipologyFilter(text);
            else
                issuePC.removeTipologyFilter(text);
        });

        return radioButton;
    }

    private RadioButton createPriorityRadioButton(String text) {
        RadioButton radioButton = this.createRadioButton(text);

        this.priorityFilterRadioButtons.add(radioButton);

        radioButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if(isSelected)
                issuePC.addPriorityFilter(text);
            else
                issuePC.removePriorityFilter(text);
        });

        return radioButton;
    }

    private RadioButton createStateRadioButton(String text) {
        RadioButton radioButton = this.createRadioButton(text);

        this.stateFilterRadioButtons.add(radioButton);

        radioButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if(isSelected)
                issuePC.addStateFilter(text);
            else
                issuePC.removeStateFilter(text);
        });

        return radioButton;
    }

    private Button createClearFiltersButton(List<RadioButton> filtersToBeCleared) {
        Button clearFiltersButton = new Button("Clear ");
        clearFiltersButton.setStyle("-fx-background-color: transparent");
        clearFiltersButton.setPadding(new Insets(10, 10, 10, 10));

        ImageView clearImageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/clear_filters.png")));
        clearImageView.setFitWidth(15);
        clearImageView.setFitHeight(15);
        clearFiltersButton.setGraphic(clearImageView);

        clearFiltersButton.setOnMouseClicked(mouseEvent -> {
            resetStateFilters(filtersToBeCleared);
        });

        return clearFiltersButton;
    }

    private void resetStateFilters(List<RadioButton> filtersToReset) {
        for(RadioButton filterToReset: filtersToReset)
            filterToReset.setSelected(false);
    }

    private Button createClearAllFiltersButton() {
        Button clearAllButton = new Button("Clear all");
        clearAllButton.setStyle("-fx-background-color: transparent");
        clearAllButton.setPadding(new Insets(10, 10, 10, 10));

        ImageView clearImageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/clear_filters.png")));
        clearImageView.setFitWidth(15);
        clearImageView.setFitHeight(15);
        clearAllButton.setGraphic(clearImageView);

        clearAllButton.setOnMouseClicked(mouseEvent -> {
            resetStateFilters(this.tipologyFilterRadioButtons);
            resetStateFilters(this.priorityFilterRadioButtons);
            resetStateFilters(this.stateFilterRadioButtons);
            filterIssues();
        });

        return clearAllButton;
    }

    private HBox createFilterButtonBox() {
        HBox filterBox = new HBox();

        Button filterButton = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/filter_2.png")));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        filterButton.setGraphic(imageView);

        filterButton.setOnMouseClicked(mouseEvent -> {
            filterIssues();
        });

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        filterBox.getChildren().addAll(
                leftSpacer,
                filterButton,
                this.createClearAllFiltersButton(),
                rightSpacer
        );

        return filterBox;
    }

    private RadioButton createRadioButton(String text, byte[] image) {
        RadioButton radioButton = this.createRadioButton(text);

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(image)));
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        radioButton.setGraphic(imageView);

        return radioButton;
    }

    private RadioButton createRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.getStyleClass().add("normal-radio");

        return radioButton;
    }

    private void filterIssues() {
        issuePC.filterIssueList();

        int numberOfPages = issuePC.getNumberOfPages();

        updatePaginationNumbers(numberOfPages);
    }

    private void updatePaginationNumbers(int numberOfPages) {
        pagination.setManaged(numberOfPages > 0);
        pagination.setVisible(numberOfPages > 0);
        noIssuesFound.setManaged(!(numberOfPages > 0));
        noIssuesFound.setVisible(!(numberOfPages > 0));

        if(numberOfPages == 0)
            pagination.setPageCount(0);
        else {
            pagination.setPageCount(numberOfPages);
            pagination.setCurrentPageIndex(0);
        }
    }

    private void setPaginationButtons(int numberOfPages) {
        pagination.setPageCount(numberOfPages);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);

        pagination.setPageFactory((pageIndex) -> {
            VBox issueContainer = new VBox();
            issueContainer.setAlignment(Pos.TOP_CENTER);
            issueContainer.setPadding(new Insets(30));

            List<IssueSummaryDTO> issueOfPageIndex = issuePC.getIssuesOfAPage(pageIndex);

            for(IssueSummaryDTO issueOfPage: issueOfPageIndex) {
                IssueSummaryCard issueCard = new IssueSummaryCard(issueOfPage);
                issueCard.setOnMouseClicked(mouseEvent -> {
                    this.clickOnAIssueToViewItsSummary(issueOfPage);
                });

                issueContainer.getChildren().add(issueCard);
            }

            return issueContainer;
        });
    }

    private void clickOnAIssueToViewItsSummary(IssueSummaryDTO issueToShow) {
        if(containerUnderTitleBar.getChildren().getLast() == centralPane)
            issuePC.showIssueSummaryPane(containerUnderTitleBar, issueToShow);
    }

    public void displayOverlayedContent(Pane newLayer) {
        containerUnderTitleBar.getChildren().add(newLayer);
    }

    public Integer getIdProject() {
        return this.idProject;
    }
}