package com.bug_board.gui.panes;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.Carousel;
import com.bug_board.utilities.JokesFooter;
import com.bug_board.utilities.MySpacer;
import com.bug_board.utilities.ProjectCard;
import com.bug_board.utilities.animations.AnimatedSearchBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class HomePagePane extends VBox {

    private final HomePagePC homePagePC;
    private static final int PROJECTS_TO_SHOW = 3;
    private List<ProjectSummaryDTO> projectsRetrieved;
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private Text heading;
    private Text hintFiltering = new Text("But you can filter them by project name...");
    private Text noProjectsFoundText = new Text();

    private Pagination pagination = new Pagination();

    public HomePagePane(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsRetrieved = projectList;
        this.setAlignment(Pos.CENTER);
        this.initialize();
    }

    private void initialize() {
        setHeading();
        setHintFiltering();
        setNoProjectsFoundText();
        setSearchProjectBar();
        setCarousel();
        addCarousel();
    }

    private void setCarousel() {
        int carouselsIndexes = (projectsRetrieved.size() + PROJECTS_TO_SHOW - 1) / PROJECTS_TO_SHOW;

        pagination.setPageCount(carouselsIndexes);
        pagination.setMaxPageIndicatorCount(5);

        pagination.setPageFactory((pageIndex) -> setProjectCardsBox(pageIndex));

        pagination.setPadding(new Insets(10));
    }

    private void setHintFiltering(){
        Region region = new Region();
        this.getChildren().add(region);
        this.getChildren().add(hintFiltering);
    }

    private void addCarousel(){
        if(!projectsRetrieved.isEmpty())
            showLatestProjects();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(
                pagination,
                noProjectsFoundText,
                spacer,
                new JokesFooter(10)
        );

        VBox.setVgrow(this, Priority.ALWAYS);
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

    private void handleNoProjectsFound() {
        noProjectsFoundText.setText("No Project Has Been Found");

        noProjectsFoundText.setVisible(true);
        noProjectsFoundText.setManaged(true);
    }

    private void setHeading(){
        if(SessionManager.getInstance().getRole().getRoleName().equals("ROLE_USER"))
            heading = new Text("You are currently working on these projects");
        else
            heading = new Text("You are currently overviewing these projects");

        heading.setId("homepage_heading");

        heading.setWrappingWidth(300);

        heading.wrappingWidthProperty().bind(this.widthProperty());

        heading.setTextAlignment(TextAlignment.CENTER);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(spacer, heading);

        this.setAlignment(Pos.CENTER);
    }

    private void setSearchProjectBar(){
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);
        searchProject.setPadding(new Insets(20));

        searchProject.setButtonAction(() -> {
            this.filterProjects(searchProject.getBarText());
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        this.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
    }

    private void filterProjects(String barText) {
        noProjectsFoundText.setVisible(false);
        noProjectsFoundText.setManaged(false);

        try {
            projectsRetrieved = homePagePC.onSearchProjectButtonClick(barText);
            System.out.println("projectsRetrieved: " + projectsRetrieved + "\nwith size: " + projectsRetrieved.size());
        }
        catch (RetrieveProjectException e) {
            noProjectsFoundText.setText(e.getMessage());
        }

        this.setCarousel();

        if(!projectsRetrieved.isEmpty()) {
            pagination.setVisible(true);
            pagination.setManaged(true);
            this.setCarousel();

            showLatestProjects();
        }
        else {
            pagination.setVisible(false);
            pagination.setManaged(false);

            noProjectsFoundText.setVisible(true);
            noProjectsFoundText.setManaged(true);

            handleNoProjectsFound();
        }
    }

    private void showLatestProjects() {
        pagination.setCurrentPageIndex(0);
    }

    private void setNoProjectsFoundText() {
        noProjectsFoundText.setStyle("-fx-fill: red; -fx-font-style: italic; -fx-font-size: 20px;");
        noProjectsFoundText.setTextAlignment(TextAlignment.CENTER);
    }
}