package com.bug_board.views;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.*;
import com.bug_board.utilities.animations.AnimatedSearchBar;
import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.implementations.ComponentButtonFactory;
import com.bug_board.utilities.buttons.factory.interfaces.IButtonsProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePageView extends MyStage {
    private final HomePagePC homePagePC;
    private List<ProjectSummaryDTO> projectsOnBoard;
    private List<ProjectSummaryDTO> projectsRetrieved;
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private VBox homePagePane = new VBox();
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private HBox projectCardsBox = new HBox();
    private Text heading;
    private Text hintFiltering = new Text("But you can filter them by project name...");
    private HBox carouselsBox = new HBox();
    private List<Button> carousel;
    private Button activeCarouselButton = null;
    private TitleBar titleBar;
    List<ProjectCard> projectsCards;

    public HomePageView(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsRetrieved = projectList;

        this.initialize();
    }



    private void initialize() {
        setFullScreen();
        setHeading();
        setHintFiltering();
        setSearchProjectBar();
        setScene();
        setCarousel();
    }

    private void setCarousel() {
        int carouselsIndexes = (projectsRetrieved.size() + 2)/3;

        if(activeCarouselButton != null){
            activeCarouselButton.getStyleClass().remove("active-carousel-button");
        }

        carousel = new ArrayList<>(3);
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
        homePagePane.getChildren().add(region);
        homePagePane.getChildren().add(hintFiltering);
    }

    private void setFullScreen(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        this.setX(screenBounds.getMinX());
        this.setY(screenBounds.getMinY());
        this.setWidth(screenBounds.getWidth());
        this.setHeight(screenBounds.getHeight());
    }

    private void setScene(){
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());

        setProjectCardsBox(0);

        root.setAlignment(Pos.TOP_CENTER);

        titleBar = new TitleBar(this, 80);
        this.setTitleBarButtons();

        homePagePane.getChildren().addAll(
                projectCardsBox,
                carouselsBox
        );

        root.getChildren().addAll(
                titleBar,
                homePagePane
        );

        this.setScene(scene);
        this.setMaximized(true);

    }

    private void setProjectCardsBox(int index) {
        projectsCards = new ArrayList<>();

        if(projectsRetrieved.size() >= (index+1) * 3)
            projectsOnBoard = projectsRetrieved.subList(index*3, (index*3)+3);
        else
            projectsOnBoard = projectsRetrieved.subList(index*3, projectsRetrieved.size());

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

        homePagePane.getChildren().add(heading);

        homePagePane.setAlignment(Pos.CENTER);
    }

    private void setSearchProjectBar(){
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);

        setSearchButtonAction();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        homePagePane.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
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

    private void setTitleBarButtons() {
        IButtonsProvider buttonProvider =  ComponentButtonFactory.getButtonFactoryByRole(
                SessionManager.getInstance().getRole().getRoleName()
        );

        for(ButtonDefinition definition: buttonProvider.createTitleBarButtons()){
            Button buttonToAdd = new Button(definition.getText());
            buttonToAdd.getStyleClass().add("title-bar-button");

            buttonToAdd.setOnMouseClicked(event -> {
                buttonActionHandler(definition.getActionId());
            });

            titleBar.getChildren().add(1, buttonToAdd);
        }
    }

    private void buttonActionHandler(String actionId) {
        switch(actionId){
            case "CREATE_LABEL":
                clickCreateLabelButton();
                break;
            case "VIEW_PERSONAL_ISSUES":
                clickViewPersonalIssuesButton();
                break;
            case "REGISTER_COLLABORATOR":
                clickRegisterCollaboratorButton();
                break;
            default:
                break;
        }
    }

    private void clickRegisterCollaboratorButton() {
    }

    private void clickViewPersonalIssuesButton() {
    }

    private void clickCreateLabelButton() {
    }
}
