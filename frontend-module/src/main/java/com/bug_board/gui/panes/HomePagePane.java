package com.bug_board.gui.panes;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.JokesFooter;
import com.bug_board.utilities.MySpacer;
import com.bug_board.utilities.ProjectCard;
import com.bug_board.utilities.animations.AnimatedSearchBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class HomePagePane extends VBox {

    private final HomePagePC homePagePC;
    private static final int PROJECTS_TO_SHOW = 3;
    private List<ProjectSummaryDTO> projectsRetrieved;
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private Label heading;
    private Label hintFiltering = new Label("But you can filter them by project name...");
    private VBox headerBox = new VBox();
    private Pagination pagination = new Pagination();
    private VBox centralPanel = new VBox();
    private StackPane carouselWrapper = new StackPane();
    private Label noProjectsFoundLabel;

    public HomePagePane(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsRetrieved = projectList;
        this.setAlignment(Pos.CENTER);
        this.initialize();
    }

    private void initialize() {
        setHeading();
        setHintFiltering();
        setSearchProjectBar();
        this.getChildren().addAll(headerBox);
        setCarousel();
        addCarousel();
        this.getChildren().add(centralPanel);
    }

    private void setCarousel() {
        int carouselsIndexes = (projectsRetrieved.size() + PROJECTS_TO_SHOW - 1) / PROJECTS_TO_SHOW;

        pagination.setPageCount(carouselsIndexes);
        pagination.setMaxPageIndicatorCount(5);

        pagination.setPageFactory((pageIndex) -> setProjectCardsBox(pageIndex));

        pagination.setPadding(new Insets(10));
    }

    private void setHintFiltering(){
        hintFiltering.setStyle("-fx-padding: 20px");
        headerBox.getChildren().add(hintFiltering);
    }

    private void addCarousel(){
        if(!projectsRetrieved.isEmpty())
            showLatestProjects();

        centralPanel.setAlignment(Pos.CENTER);

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        createNoProjectsFoundLabel();

        if(projectsRetrieved.isEmpty()) {
            carouselWrapper.getChildren().add(createNoProjectsFoundBox());
        }
        else
            carouselWrapper.getChildren().add(pagination);

        centralPanel.getChildren().addAll(
                searchProject,
                topSpacer,
                carouselWrapper,
                bottomSpacer,
                new JokesFooter(10)
        );

        VBox.setVgrow(centralPanel, Priority.ALWAYS);
    }

    private Node createNoProjectsFoundBox() {
        VBox noProjectsFoundBox = new VBox();

        ImageView noProjectsFoundIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/not_found.png")));

        noProjectsFoundLabel = createNoProjectsFoundLabel();

        noProjectsFoundBox.getChildren().addAll(noProjectsFoundIcon, noProjectsFoundLabel);
        noProjectsFoundBox.setAlignment(Pos.CENTER);

        return noProjectsFoundBox;
    }

    private Node setProjectCardsBox(int index) {
        List<ProjectSummaryDTO> projectList = homePagePC.getProjectsOfAPage(index);

        HBox projectCardsBox = new HBox();

        if(!projectList.isEmpty())
            for(ProjectSummaryDTO project: projectList){
                ProjectCard currentProjectCard = new ProjectCard(project, homePagePC);
                currentProjectCard.setPadding(new Insets(15));
                projectCardsBox.getChildren().add(currentProjectCard);
            }

        projectCardsBox.setAlignment(Pos.CENTER);
        projectCardsBox.setPadding(new Insets(50));

        return projectCardsBox;
    }

    private Label createNoProjectsFoundLabel() {
        noProjectsFoundLabel = new Label();
        noProjectsFoundLabel.setText("No projects found");
        noProjectsFoundLabel.setStyle("-fx-font-style: italic; -fx-font-size: 20px;");
        noProjectsFoundLabel.setAlignment(Pos.CENTER);
        return noProjectsFoundLabel;
    }

    private void setHeading(){
        if(SessionManager.getInstance().getRole().getRoleName().equals("ROLE_USER"))
            heading = new Label("You are currently working on these projects");
        else
            heading = new Label("You are currently overviewing these projects");

        heading.setId("homepage_heading");

        heading.setTextAlignment(TextAlignment.CENTER);


        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(heading);


        this.setAlignment(Pos.CENTER);
    }

    private void setSearchProjectBar(){
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);
        searchProject.setTextFieldPrompt("Search project");

        searchProject.setSearchButtonAction(() -> this.filterProjects(searchProject.getBarText()));
        searchProject.setClearButtonAction(() -> filterProjects(""));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        headerBox.getChildren().addAll(MySpacer.createVSpacer());
    }

    private void filterProjects(String barText) {
        projectsRetrieved = homePagePC.onSearchProjectButtonClick(barText);

        homePagePC.setProjectsRetrieved(projectsRetrieved);

        if(!projectsRetrieved.isEmpty()) {
            carouselWrapper.getChildren().removeLast();
            carouselWrapper.getChildren().add(pagination);

            this.setCarousel();

            showLatestProjects();
        }
        else {
            carouselWrapper.getChildren().removeLast();
            carouselWrapper.getChildren().add(createNoProjectsFoundBox());
        }

    }

    private void showLatestProjects() {
        pagination.setCurrentPageIndex(0);
    }
}