package com.bug_board.views;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.*;
import com.bug_board.utilities.animations.AnimatedSearchBar;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

public class HomePageView extends MyStage {
    private final HomePagePC homePagePC;
    private List<ProjectSummaryDTO> projectsOnBoard;
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private VBox homePagePane = new VBox();
    private AnimatedSearchBar searchProject =  new AnimatedSearchBar();
    private HBox projectCardsBox = new HBox();
    private Text heading;
    List<ProjectCard> projectsCards;

    public HomePageView(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsOnBoard = projectList;

        this.initialize();
    }



    private void initialize() {
        setFullScreen();
        setSearchProjectBar();
        setHeading();
        setScene();
    }

    public void setFullScreen(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        this.setX(screenBounds.getMinX());
        this.setY(screenBounds.getMinY());
        this.setWidth(screenBounds.getWidth());
        this.setHeight(screenBounds.getHeight());
    }

    public void setScene(){
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());

        setProjectCardsBox();

        root.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(        new TitleBar(this, 80),
                homePagePane
        );

        this.setScene(scene);
        this.setMaximized(true);

    }

    private void setProjectCardsBox() {
        projectsCards = new ArrayList<>();
        for(ProjectSummaryDTO project: projectsOnBoard){
            projectsCards.add(new ProjectCard(project));
        }

        projectCardsBox.getChildren().clear();
        projectCardsBox.getChildren().addAll(projectsCards);

        homePagePane.getChildren().remove(projectCardsBox);
        homePagePane.getChildren().add(projectCardsBox);
    }

    public void setHeading(){
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

    public void setSearchProjectBar(){
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
            projectsOnBoard = homePagePC.onSearchProjectButtonClick(barText);
        } catch (HTTPSendException e) {
            throw new RuntimeException(e);
        } catch (BadConversionToDTOException e) {
            throw new RuntimeException(e);
        } catch (BackendErrorException e) {
            throw new RuntimeException(e);
        }

        projectsCards.clear();
        setProjectCardsBox();
    }
}
