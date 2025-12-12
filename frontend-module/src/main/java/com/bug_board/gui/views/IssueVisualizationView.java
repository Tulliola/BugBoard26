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
    private BorderPane containerUnderTitleBar;
    private VBox issueContainer;
    private HBox paginationNumberContainer;
    private ToggleGroup paginationButtonGroup;
    private String headingText;
    private Integer idProject;

    private List<RadioButton> tipologyFilterRadioButtons = new ArrayList<>();
    private List<RadioButton> priorityFilterRadioButtons = new ArrayList<>();
    private List<RadioButton> stateFilterRadioButtons = new ArrayList<>();

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


        titleBar = new TitleBar(this, 80);
        this.setGoBackButton();

        BorderPane containerUnderTitleBar = this.createContainerUnderTitleBar();
        containerUnderTitleBar.setPadding(new Insets(10, 0, 0, 0));
        VBox.setVgrow(containerUnderTitleBar, Priority.ALWAYS);

        root.getChildren().addAll(
                titleBar,
                containerUnderTitleBar
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

    private BorderPane createContainerUnderTitleBar() {
        containerUnderTitleBar = new BorderPane();

        containerUnderTitleBar.setLeft(this.createSideFilterBar());
        containerUnderTitleBar.setCenter(this.createCentralPane());
        containerUnderTitleBar.setRight(this.createLateralPane());

        return containerUnderTitleBar;
    }

    private VBox createCentralPane() {
        VBox centralPane = new VBox();
        centralPane.setAlignment(Pos.TOP_CENTER);
        centralPane.setStyle("-fx-background-color: white; -fx-spacing: 20px");

        Label heading = new Label(this.headingText);
        heading.setStyle("-fx-font-size: 30px");
        heading.setPadding(new Insets(10, 10, 10, 10));
        heading.setWrapText(true);

        List<IssueSummaryDTO> issueSummaryDTOList = issuePC.getIssuesOfAPage(0);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);

        centralPane.getChildren().addAll(
                heading,
                this.createIssueContainer(issueSummaryDTOList),
                spacer,
                this.createPaginationNumbersContainer(),
                spacer2
        );

        return centralPane;
    }

    private VBox createIssueContainer(List<IssueSummaryDTO> issueSummaryDTOList) {
        issueContainer = new VBox();
        issueContainer.setStyle("-fx-background-color: white");
        issueContainer.setAlignment(Pos.TOP_CENTER);

        if(issueSummaryDTOList.isEmpty()){
            Label noIssuesFound = new Label("No issues found");
            issueContainer.getChildren().add(noIssuesFound);
        }
        else
            for(IssueSummaryDTO issueDTO: issueSummaryDTOList)
                issueContainer.getChildren().add(new IssueSummaryCard(issueDTO));

        return issueContainer;
    }

    private HBox createPaginationNumbersContainer() {
        paginationNumberContainer = new HBox();
        paginationNumberContainer.setAlignment(Pos.CENTER);

        int numberOfPages = this.issuePC.getTotalNumberOfPages();

        paginationButtonGroup = new ToggleGroup();

        this.setPaginationButtons(numberOfPages);

        return paginationNumberContainer;
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

    private Label createNoIssuesFoundLabel() {
        Label noIssuesFound = new Label("No issues found");

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

    private HBox createFilterButtonBox() {
        HBox filterBox = new HBox();

        Button filterButton = new Button("Filter");
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
        List<IssueSummaryDTO> allFilteredIssues = issuePC.getFilteredIssueList();

        updateIssueListInCarousel(issuePC.extractFirstPageIssues(allFilteredIssues));
        updatePaginationNumbers(allFilteredIssues);
    }

    private void updateIssueListInCarousel(List<IssueSummaryDTO> newIssuesToShow) {
        this.issueContainer.getChildren().clear();

        if(newIssuesToShow == null || newIssuesToShow.isEmpty())
            this.issueContainer.getChildren().add(this.createNoIssuesFoundLabel());
        else
            for(IssueSummaryDTO newIssueToShow: newIssuesToShow)
                this.issueContainer.getChildren().add(new IssueSummaryCard(newIssueToShow));
    }

    private void updatePaginationNumbers(List<IssueSummaryDTO> newIssuesToShow) {
        this.paginationNumberContainer.getChildren().clear();

        int numberOfPages = this.issuePC.getNumberOfPagesOfAGivenSublist(newIssuesToShow);

        this.setPaginationButtons(numberOfPages);
    }

    private void setPaginationButtons(int numberOfPages) {
        for(int i = 0; i < numberOfPages; i++){
            final int page = i;
            ToggleButton currentPageButton = new ToggleButton(String.valueOf(i+1));
            currentPageButton.getStyleClass().add("pagination-button");
            currentPageButton.setPrefSize(100, 100);
            currentPageButton.setPadding(new Insets(10, 10, 10, 10));

            currentPageButton.setOnMouseClicked(mouseEvent -> {
                List<IssueSummaryDTO> issuesInPage = issuePC.getIssuesOfAPage(page);

                this.updateIssueListInCarousel(issuesInPage);
            });

            currentPageButton.setToggleGroup(paginationButtonGroup);

            paginationNumberContainer.getChildren().add(currentPageButton);
        }

        if(!paginationNumberContainer.getChildren().isEmpty())
            paginationButtonGroup.getToggles().getFirst().setSelected(true);
    }

    public Integer getIdProject() {
        return this.idProject;
    }
}
