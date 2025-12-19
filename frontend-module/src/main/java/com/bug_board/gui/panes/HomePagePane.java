package com.bug_board.gui.panes;

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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class HomePagePane extends VBox {

    private final HomePagePC homePagePC;
    private static final int PROJECTS_TO_SHOW = 3;
    private List<ProjectSummaryDTO> projectsOnBoard;
    private List<ProjectSummaryDTO> projectsRetrieved;
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private HBox projectCardsBox = new HBox();
    private Text heading;
    private Text hintFiltering = new Text("But you can filter them by project name...");
    private HBox carouselsBox = new HBox();
    private List<ProjectCard> projectsCards = new ArrayList<>();
    private Text noProjectsFoundText = new Text("No Project Has Been Found");

    private Carousel carousel;

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

        carousel = new Carousel(carouselsIndexes);

        carouselsBox.setPadding(new Insets(5));
        carouselsBox.setSpacing(5);
        carouselsBox.setAlignment(Pos.CENTER);

        carouselsBox.getChildren().clear();
        carouselsBox.getChildren().addAll(carousel);

        for(int i = 0; i < carouselsIndexes; i++) {
            int finalI = i;
            carousel.setButtonAction(i, () -> setProjectCardsBox(finalI));
        }
    }

    private void setHintFiltering(){
        Region region = new Region();
        this.getChildren().add(region);
        this.getChildren().add(hintFiltering);
    }

    private void addCarousel(){
        if(!projectsRetrieved.isEmpty())
            showLatestProjects();
        else
            showNoResults();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(
                projectCardsBox,
                carouselsBox,
                spacer,
                new JokesFooter(10)
        );
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private void setProjectCardsBox(int index) {
        projectsCards.clear();

        if(projectsRetrieved.size() >= (index+1) * PROJECTS_TO_SHOW)
            projectsOnBoard = projectsRetrieved.subList(index * PROJECTS_TO_SHOW, (index * PROJECTS_TO_SHOW) + PROJECTS_TO_SHOW);
        else if(!projectsRetrieved.isEmpty())
            projectsOnBoard = projectsRetrieved.subList(index * PROJECTS_TO_SHOW, projectsRetrieved.size());
        else{
            this.handleNoProjectsFound();
        }

        if(projectsOnBoard != null && !projectsOnBoard.isEmpty())
            for(ProjectSummaryDTO project: projectsOnBoard){
                projectsCards.add(new ProjectCard(project, this.homePagePC));
            }

        projectCardsBox.setAlignment(Pos.CENTER);

        projectCardsBox.setPadding(new Insets(50));

        addProjectCardsToBox();
    }

    private void handleNoProjectsFound() {
        noProjectsFoundText.setText("No Projects Found");
        projectCardsBox.getChildren().add(noProjectsFoundText);
    }

    private void addProjectCardsToBox(){
        projectCardsBox.getChildren().clear();

        if(projectsCards != null && !projectsCards.isEmpty())
            for(ProjectCard card: projectsCards){
                card.setPadding(new Insets(15));
                projectCardsBox.getChildren().add(card);
            }
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

        searchProject.setButtonAction(() -> {
            this.filterProjects(searchProject.getBarText());
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        this.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
    }

    private void filterProjects(String barText) {
        if(projectCardsBox.getChildren().contains(noProjectsFoundText)){
            projectCardsBox.getChildren().remove(noProjectsFoundText);
        }

        try {
            projectsRetrieved = homePagePC.onSearchProjectButtonClick(barText);
        }
        catch (RetrieveProjectException e) {
            noProjectsFoundText.setText(e.getMessage());
        }

        projectCardsBox.getChildren().clear();

        if(!projectsRetrieved.isEmpty())
            showLatestProjects();
        else
            showNoResults();
    }

    private void showLatestProjects() {
        setProjectCardsBox(0);
        setCarousel();
    }

    private void showNoResults() {
        projectCardsBox.getChildren().removeAll(projectsCards);
        projectCardsBox.setPrefHeight(550);
        projectCardsBox.getChildren().add(noProjectsFoundText);
        carouselsBox.getChildren().clear();
    }

    private void setNoProjectsFoundText() {
        noProjectsFoundText.setStyle("-fx-fill: red; -fx-font-style: italic; -fx-font-size: 20px;");
        noProjectsFoundText.setTextAlignment(TextAlignment.CENTER);
    }
}