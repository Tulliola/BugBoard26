package com.bug_board.gui.panes;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.MySpacer;
import com.bug_board.utilities.ProjectCard;
import com.bug_board.utilities.animations.AnimatedSearchBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class HomePagePane extends VBox {

    private final HomePagePC homePagePC;
    private static final int PROJECTS_TO_SHOW = 5;
    private List<ProjectSummaryDTO> projectsOnBoard;
    private List<ProjectSummaryDTO> projectsRetrieved;
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private HBox projectCardsBox = new HBox();
    private Text heading;
    private Text hintFiltering = new Text("But you can filter them by project name...");
    private HBox carouselsBox = new HBox();
    private List<Button> carousel;
    private Button activeCarouselButton = null;
    List<ProjectCard> projectsCards;

    public HomePagePane(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsRetrieved = projectList;

        this.initialize();
    }

    private void initialize() {
        setHeading();
        setHintFiltering();
        setSearchProjectBar();
        addCarousel();
        setCarousel();
    }

    private void setCarousel() {
        int carouselsIndexes = (projectsRetrieved.size() + PROJECTS_TO_SHOW - 1) / PROJECTS_TO_SHOW;

        if(activeCarouselButton != null){
            activeCarouselButton.getStyleClass().remove("active-carousel-button");
        }

        carousel = new ArrayList<>(PROJECTS_TO_SHOW);
        setCarouselsButtons(carouselsIndexes);
        
        carouselsBox.setPadding(new Insets(5));
        carouselsBox.setSpacing(5);
        carouselsBox.setAlignment(Pos.CENTER);

        carouselsBox.getChildren().clear();
        carouselsBox.getChildren().addAll(carousel);

        if(activeCarouselButton == null) {
            setActiveButton(carousel.get(0));
        }

    }

    private void setActiveButton(Button button) {
        button.getStyleClass().add("active-carousel-button");
        activeCarouselButton = button;
    }

    private void setCarouselsButtons(int numOfButtons){
        for (int i = 0; i < numOfButtons; i++) {
            Button carouselButton = new Button(String.valueOf(i + 1));
            carouselButton.getStyleClass().add("carousel-button");

            carouselButton.getStyleClass().add("carousel-button-hover");

            int finalI = i;
            carouselButton.setOnAction(e -> {
                setProjectCardsBox(finalI);
                handleClickButtonGraphics(carouselButton);
            });

            carousel.add(carouselButton);
        }
    }

    private void handleClickButtonGraphics(Button carouselButton) {
        if(activeCarouselButton != null) {
            activeCarouselButton.getStyleClass().remove("active-carousel-button");
        }

        setActiveButton(carouselButton);
    }

    private void setHintFiltering(){
        Region region = new Region();
        this.getChildren().add(region);
        this.getChildren().add(hintFiltering);
    }

    private void addCarousel(){

        setProjectCardsBox(0);

        this.getChildren().addAll(
                projectCardsBox,
                carouselsBox
        );

        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);

    }

    private void setProjectCardsBox(int index) {
        projectsCards = new ArrayList<>();

        if(projectsRetrieved.size() >= (index+1) * PROJECTS_TO_SHOW)
            projectsOnBoard = projectsRetrieved.subList(index * PROJECTS_TO_SHOW, (index * PROJECTS_TO_SHOW) + PROJECTS_TO_SHOW);
        else
            projectsOnBoard = projectsRetrieved.subList(index * PROJECTS_TO_SHOW, projectsRetrieved.size());

        for(ProjectSummaryDTO project: projectsOnBoard){
            projectsCards.add(new ProjectCard(project));
        }

        projectCardsBox.setAlignment(Pos.CENTER);

        projectCardsBox.getChildren().clear();
        projectCardsBox.setPadding(new Insets(50));

        addProjectCardsToBox();
    }

    private void addProjectCardsToBox(){
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

        this.getChildren().add(heading);

        this.setAlignment(Pos.CENTER);
    }

    private void setSearchProjectBar(){
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);

        setSearchButtonAction();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        this.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
    }

    private void setSearchButtonAction(){
        searchProject.setButtonAction(() -> {
            this.filterProjects(searchProject.getBarText());
        });
    }

    private void filterProjects(String barText) {
        try {
            projectsRetrieved = homePagePC.onSearchProjectButtonClick(barText);
        }
        catch (HTTPSendException e) {
            throw new RuntimeException(e);
        }
        catch (BadConversionToDTOException e) {
            throw new RuntimeException(e);
        }
        catch (BackendErrorException e) {
            throw new RuntimeException(e);
        }

        projectsCards.clear();
        setProjectCardsBox(0);
        setCarousel();
    }
}
