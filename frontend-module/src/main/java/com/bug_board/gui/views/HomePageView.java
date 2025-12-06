package com.bug_board.gui.views;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.gui.panes.HomePagePane;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.*;
import com.bug_board.utilities.buttons.ButtonDefinition;
import com.bug_board.utilities.buttons.factory.implementations.ComponentButtonFactory;
import com.bug_board.utilities.buttons.factory.interfaces.IButtonsProvider;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import java.util.List;

public class HomePageView extends MyStage {
    private final HomePagePC homePagePC;
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private final HomePagePane homePagePane;
    private TitleBar titleBar;

    public HomePageView(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        homePagePane = new HomePagePane(homePagePC, projectList);

        this.initialize();
    }

    private void initialize() {
        setFullScreen();
        setScene();
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

        root.setAlignment(Pos.TOP_CENTER);

        titleBar = new TitleBar(this, 80);
        this.setTitleBarButtons();

        homePagePane.setAlignment(Pos.CENTER);
        VBox.setVgrow(homePagePane, Priority.ALWAYS);

        root.getChildren().addAll(
                titleBar,
                homePagePane
        );

        this.setScene(scene);
        this.setMaximized(true);

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

            titleBar.addButtonToTitleBar(buttonToAdd);
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
