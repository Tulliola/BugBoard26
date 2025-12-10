package com.bug_board.gui.views;

import com.bug_board.dto.IssueSummaryDTO;
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

import java.util.List;

public class IssueVisualizationView extends MyStage {

    private final IssueVisualizationPC issuePC;

    private final VBox root = new VBox();
    private final Scene scene = new Scene(root);
    private TitleBar titleBar;
    private BorderPane containerUnderTitleBar;
    private VBox issueContainer;
    private HBox paginationNumberContainer;
    private final String headingText;

    public IssueVisualizationView(IssueVisualizationPC issuePC, String headingText) {
        this.issuePC = issuePC;
        this.headingText = headingText;

        issuePC.setView(this);

        this.initialize();
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
                this.createPaginationNumbersContainer(issueSummaryDTOList),
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

    private HBox createPaginationNumbersContainer(List<IssueSummaryDTO> issueList) {
        paginationNumberContainer = new HBox();
        paginationNumberContainer.setAlignment(Pos.CENTER);

        int numberOfPages = this.issuePC.getTotalNumberOfPages();

        ToggleGroup buttonsGroup = new ToggleGroup();

        for(int i = 0; i < numberOfPages; i++){
            final int page = i;
            ToggleButton currentPageButton = new ToggleButton(String.valueOf(i+1));
            currentPageButton.getStyleClass().add("pagination-button");
            currentPageButton.setPrefSize(100, 100);
            currentPageButton.setPadding(new Insets(10, 10, 10, 10));

            currentPageButton.setOnMouseClicked(mouseEvent -> {
                List<IssueSummaryDTO> issuesInPage = issuePC.getIssuesOfAPage(page);

                this.updateCarousel(issuesInPage);
            });

            currentPageButton.setToggleGroup(buttonsGroup);

            paginationNumberContainer.getChildren().add(currentPageButton);
        }

        if(!paginationNumberContainer.getChildren().isEmpty())
           buttonsGroup.getToggles().getFirst().setSelected(true);

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
        filterBar.setPrefWidth(200);
        filterBar.setMinWidth(200);
        filterBar.setMaxWidth(200);

        Label filterLabel = new Label("Filter by");
        filterLabel.setPadding(new Insets(10, 10, 10, 10));
        filterLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: -color-primary");

        ImageView filterPic = new ImageView(new Image(getClass().getResourceAsStream("/icons/filter.png")));
        filterPic.setFitWidth(50);
        filterPic.setFitHeight(50);
        filterLabel.setGraphic(filterPic);

        filterBar.getChildren().addAll(
                filterLabel,
                this.createTipologyFilterBox(),
                this.createPriorityFilterBox(),
                this.createStateFilterBox()
        );

        return filterBar;
    }

    private VBox createTipologyFilterBox() {
        VBox tipologyFilterBar = new VBox();
        tipologyFilterBar.getChildren().add(this.createFilterLabel("Tipology"));

        List<String> tipologies = issuePC.getTipologyStrings();

        for(String tipology: tipologies)
            tipologyFilterBar.getChildren().add(this.createRadioButton(tipology));

        return tipologyFilterBar;
    }

    private VBox createPriorityFilterBox() {
        VBox priorityFilterBar = new VBox();
        priorityFilterBar.getChildren().add(this.createFilterLabel("Priority"));

        List<String> priorities = issuePC.getPriorityStrings();

        for(String priority: priorities)
            priorityFilterBar.getChildren().add(this.createRadioButton(priority));

        return priorityFilterBar;
    }

    private VBox createStateFilterBox() {
        VBox stateFilterBox = new VBox();
        stateFilterBox.getChildren().add(this.createFilterLabel("State"));

        List<String> states = issuePC.getStateStrings();

        for(String state: states)
            stateFilterBox.getChildren().add(this.createRadioButton(state));

        return stateFilterBox;
    }

    private Label createFilterLabel(String labelText) {
        Label filterLabel = new Label(labelText);
        filterLabel.setPadding(new Insets(10, 10, 10, 10));
        filterLabel.setStyle("-fx-font-size: 16px");

        return filterLabel;
    }

    private RadioButton createRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.getStyleClass().add("normal-radio");

        return radioButton;
    }

    private void updateCarousel(List<IssueSummaryDTO> newIssuesToShow) {
        updateIssueListInCarousel(newIssuesToShow);
    }

    private void updateIssueListInCarousel(List<IssueSummaryDTO> newIssuesToShow) {
        this.issueContainer.getChildren().clear();

        if(newIssuesToShow.isEmpty())
            this.issueContainer.getChildren().add(this.createNoIssuesFoundLabel());
        else
            for(IssueSummaryDTO newIssueToShow: newIssuesToShow)
                this.issueContainer.getChildren().add(new IssueSummaryCard(newIssueToShow));
    }
}
