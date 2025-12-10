package com.bug_board.gui.views;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.presentation_controllers.IssueVisualizationPC;
import com.bug_board.utilities.IssueSummaryCard;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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

        containerUnderTitleBar.setCenter(this.createCentralPane());
        containerUnderTitleBar.setLeft(this.createLateralPane());
        containerUnderTitleBar.setRight(this.createLateralPane());

        return containerUnderTitleBar;
    }

    private VBox createCentralPane() {
        VBox centralPane = new VBox();
        centralPane.setAlignment(Pos.TOP_CENTER);
        centralPane.setStyle("-fx-background-color: green; -fx-spacing: 20px");

        Label heading = new Label(this.headingText);
        heading.setStyle("-fx-font-size: 30px");
        heading.setPadding(new Insets(10, 10, 10, 10));
        heading.setWrapText(true);

        List<IssueSummaryDTO> issueSummaryDTOList = issuePC.getIssuesOfAPage(0);

        centralPane.getChildren().addAll(
                heading,
                this.createIssueContainer(issueSummaryDTOList),
                this.createPaginationNumbersContainer(issueSummaryDTOList)
        );

        return centralPane;
    }

    private VBox createIssueContainer(List<IssueSummaryDTO> issueSummaryDTOList) {
        issueContainer = new VBox();
        issueContainer.setStyle("-fx-background-color: blue");
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

        int numberOfPages = this.issuePC.getTotalNumberOfPages();

        for(int i = 0; i < numberOfPages; i++){
            final int page = i;
            Button currentPageButton = new Button(String.valueOf(i+1));

            currentPageButton.setOnMouseClicked(mouseEvent -> {
                List<IssueSummaryDTO> issuesInPage = issuePC.getIssuesOfAPage(page);

                this.updateCarousel(issuesInPage);
            });

            paginationNumberContainer.getChildren().add(currentPageButton);
        }

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
