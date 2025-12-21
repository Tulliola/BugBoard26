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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import java.util.List;

public class HomePageView extends MyStage {
    private final HomePagePC homePagePC;
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private StackPane containerUnderTitleBar = new StackPane();
    private final HomePagePane homePagePane;
    private TitleBar titleBar;

    public HomePageView(HomePagePC homePagePC, List<ProjectSummaryDTO> projectList) {
        this.homePagePC = homePagePC;
        homePagePC.setView(this);

        homePagePane = new HomePagePane(homePagePC, projectList);

        VBox.setVgrow(containerUnderTitleBar, Priority.ALWAYS);
        containerUnderTitleBar.getChildren().add(homePagePane);

        this.initialize();
    }

    private void initialize() {
        setFullScreen();
        setScene();
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
                containerUnderTitleBar
        );

        this.setScene(scene);
        this.setMaximized(true);

    }

    private void setTitleBarButtons() {
        IButtonsProvider buttonProvider =  ComponentButtonFactory.getButtonFactoryByRole(
                SessionManager.getInstance().getRole().getRoleName()
        );

        ToggleGroup groupButtons = new ToggleGroup();

        for(ButtonDefinition definition: buttonProvider.createTitleBarButtons()){
            ToggleButton buttonToAdd = new ToggleButton(definition.getText());

            buttonToAdd.setOnMouseClicked(event -> {
                buttonActionHandler(definition.getActionId());
            });

            buttonToAdd.setToggleGroup(groupButtons);

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
            case "VIEW_LABELS":
                clickViewAllLabelsButton();
                break;
            default:
                break;
        }
    }

    private void clickRegisterCollaboratorButton() {
        if(containerUnderTitleBar.getChildren().getLast() == homePagePane)
            homePagePC.showRegisterUserOverlay(containerUnderTitleBar);
    }

    private void clickViewPersonalIssuesButton() {
        homePagePC.onViewPersonalIssuesButtonClicked();
    }

    private void clickCreateLabelButton() {
        if(containerUnderTitleBar.getChildren().getLast() ==  homePagePane)
            homePagePC.showLabelCreationOverlay(containerUnderTitleBar);
    }

    private void clickViewAllLabelsButton() {
        if(containerUnderTitleBar.getChildren().getLast() == homePagePane)
            homePagePC.showAllLabelsOverlay(containerUnderTitleBar);
    }

    public void displayOverlayedContent(Node newLayer) {
        containerUnderTitleBar.getChildren().add(newLayer);
    }

    public StackPane getContainerUnderTitleBar() {

        return containerUnderTitleBar;
    }
}
