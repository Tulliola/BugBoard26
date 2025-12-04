package com.bug_board.views;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.ProjectCard;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.List;

public class HomePageView extends MyStage {
    private final HomePagePC homePagePC;
    private final List<ProjectSummaryDTO> projectsOnBoard;

    VBox vBox = new VBox();
    Scene scene = new Scene(vBox);

    public HomePageView(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        this.projectsOnBoard = projectList;

        this.initialize();
    }

    private void initialize(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        this.setX(screenBounds.getMinX());
        this.setY(screenBounds.getMinY());
        this.setWidth(screenBounds.getWidth());
        this.setHeight(screenBounds.getHeight());
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());

        vBox.getChildren().addAll(
                new TitleBar(this, 80),
                new ProjectCard(this.projectsOnBoard.getLast())
        );

        this.setScene(scene);
        this.setMaximized(true);
    }
}
