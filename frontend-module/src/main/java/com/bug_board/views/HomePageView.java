package com.bug_board.views;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.utilities.MySpacer;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.ProjectCard;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private VBox vBox = new VBox();
    private Scene scene = new Scene(vBox);
    private VBox internVBox = new VBox();
    private TextField searchProject =  new TextField();
    private HBox projectCardsBox = new HBox();
    private Text heading = new Text("You are currently working on these projects");


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

        vBox.getChildren().addAll(
                new TitleBar(this, 80),
                internVBox
        );

        this.setScene(scene);
        this.setMaximized(true);

    }

    private void setProjectCardsBox() {
        ArrayList<ProjectCard> projectCards = new ArrayList<>();
        projectCards.add(new ProjectCard(this.projectsOnBoard.getLast()));
        projectCardsBox.getChildren().addAll(projectCards);
        internVBox.getChildren().addAll(MySpacer.createVSpacer(), MySpacer.createVSpacer(), projectCardsBox);
    }

    public void setHeading(){
        heading.setId("homepage_heading");
        heading.setWrappingWidth(300);
        heading.wrappingWidthProperty().bind(this.widthProperty());
        heading.setTextAlignment(TextAlignment.CENTER);
        internVBox.getChildren().add(heading);
        internVBox.setAlignment(Pos.CENTER);
    }

    public void setSearchProjectBar(){
        searchProject.setPromptText("Search Project");
        searchProject.setAlignment(Pos.CENTER);
        searchProject.setPrefWidth(300);
        searchProject.setMaxWidth(300);
        Image searchIcon = new Image(getClass().getResourceAsStream("/icons/user_icon.png"));
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitHeight(16);
        searchIconView.setFitWidth(16);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(searchProject);
        stackPane.getChildren().add(searchIconView);
        stackPane.setAlignment(searchIconView, Pos.CENTER_RIGHT);
        StackPane.setMargin(searchIconView, new Insets(0, 8, 0, 0));
        searchProject.setStyle("-fx-padding: 0.5em 25px 0.5em 0.5em;");
        internVBox.getChildren().addAll(MySpacer.createVSpacer(), searchProject);
    }
}
