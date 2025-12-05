package com.bug_board.views;

import com.bug_board.dto.ProjectSummaryDTO;
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
    private final List<ProjectSummaryDTO> projectsOnBoard;
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private VBox internVBox = new VBox();
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
                internVBox
        );

        this.setScene(scene);
        this.setMaximized(true);

    }

    private void setProjectCardsBox() {
        projectsCards = new ArrayList<>();
        projectsCards.add(new ProjectCard(this.projectsOnBoard.getLast()));
        projectCardsBox.getChildren().addAll(projectsCards);
        internVBox.getChildren().addAll(MySpacer.createVSpacer(), MySpacer.createVSpacer(), projectCardsBox);
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
        internVBox.getChildren().add(heading);
        internVBox.setAlignment(Pos.CENTER);
    }

    public void setSearchProjectBar(){
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        internVBox.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
    }
}
