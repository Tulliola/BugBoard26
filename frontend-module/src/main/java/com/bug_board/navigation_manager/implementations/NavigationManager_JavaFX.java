package com.bug_board.navigation_manager.implementations;

import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.architectural_controllers.UserRegistrationController;
import com.bug_board.architectural_controllers.*;
import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.*;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.architectural_controllers.RetrieveLabelsException;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.factories.ProjectFactory;
import com.bug_board.gui.panes.*;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.gui.views.LoginView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.presentation_controllers.*;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.gui.views.HomePageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NavigationManager_JavaFX implements INavigationManager {

    @Override
    public void navigateToHomePage() {
        UserProjectController projectController = new UserProjectController(new UserProjectDAO_REST(new MyHTTPClient()));
        HomePagePC homePagePC = new HomePagePC(projectController, this);

        List<ProjectSummaryDTO> projectsList;

        try {
            projectsList = ProjectFactory.getInstance()
                    .setProjectController(projectController)
                    .getProjectsOnBoardByRole(SessionManager.getInstance().getRole());

            homePagePC.setProjectsRetrieved(projectsList);

            HomePageView homePageView = new HomePageView(
                    homePagePC,
                    projectsList
            );

            homePageView.show();
        }
        catch (RetrieveProjectException exc) {
            homePagePC.showErrorAlert(exc.getMessage(), exc.getTechnicalMessage());
        }
    }

    @Override
    public void navigateToViewPersonalIssues() {
        UserIssueController issueController = new UserIssueController(new UserIssueDAO_REST(new MyHTTPClient()));
        IssueVisualizationPC issuePC = new PersonalIssueVisualizationPC(this, issueController);

        try {
            List<IssueSummaryDTO> personalIssues = issueController.getPersonalIssues(null);
            issuePC.setIssueList(personalIssues);

            IssueVisualizationView issueView = new IssueVisualizationView(
                    issuePC,
                    "Issues reported by you"
            );

            issueView.show();
        }
        catch (RetrieveIssuesException exc) {
            issuePC.showErrorAlert(exc.getMessage(), exc.getTechnicalMessage());
        }
    }

    @Override
    public void navigateToViewProjectIssues(Integer idProject, String projectName) {
        ProjectIssueController projectIssueController = new ProjectIssueController(new ProjectIssueDAO_REST(new MyHTTPClient()));
        IssueVisualizationPC issuePC = new ProjectIssueVisualizationPC(this, projectIssueController);

        try {
            List<IssueSummaryDTO> projectIssues = projectIssueController.getProjectIssues(idProject, null);
            issuePC.setIssueList(projectIssues);

            IssueVisualizationView issueView = new IssueVisualizationView(
                    issuePC,
                    "Project \"" + projectName + "\" \'s issues",
                    idProject
            );

            issueView.show();
        }
        catch (RetrieveIssuesException exc) {
            issuePC.showErrorAlert(exc.getMessage(), exc.getTechnicalMessage());
        }
    }

    @Override
    public void closeWindow(Object windowToClose){
        if(windowToClose instanceof Stage)
            ((Stage) windowToClose).close();
    }

    @Override
    public Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC) {
        UserLabelController labelController = new UserLabelController(new UserLabelDAO_REST(new MyHTTPClient()));

        LabelManagementPC labelPC = new LabelManagementPC(this, labelController);

        LabelCreationFormPane labelCreationFormPane = new LabelCreationFormPane(labelPC, parentContainer);

        return labelCreationFormPane;
    }

    @Override
    public Pane buildRegisterUserComponent(StackPane parentContainer, HomePagePC parentPC) {
        UserRegistrationController userRegistrationController = new UserRegistrationController(new UserDAO_REST(new MyHTTPClient()));

        UserRegistrationPC userRegistrationPC = new UserRegistrationPC(userRegistrationController);

        UserRegistrationFormPane userRegistrationPane = new UserRegistrationFormPane(parentContainer, userRegistrationPC);

        userRegistrationPC.setPane(userRegistrationPane);

        return userRegistrationPane;
    }

    @Override
    public Pane buildReportIssueComponent(StackPane parentContainer, int projectToReport) {
        ReportIssueController reportIssueController = new ReportIssueController(new ProjectIssueDAO_REST(new MyHTTPClient()), new UserLabelDAO_REST(new MyHTTPClient()));

        ReportIssuePC reportIssuePC = new ReportIssuePC(this, reportIssueController, projectToReport);

        try {
            reportIssuePC.setUserLabels();

            return new ReportIssuePane(parentContainer, reportIssuePC);
        }
        catch (RetrieveLabelsException e) {
            closeWindow(parentContainer.getParent().getScene().getWindow());
            reportIssuePC.showErrorAlert(e.getMessage(), e.getTechnicalMessage());
        }

        return new Pane();
    }

    @Override
    public Pane buildIssueSummaryComponent(StackPane containerUnderTitleBar, IssueSummaryDTO issueToShow, IssueVisualizationPC issuePC) {
        return new IssueSummaryPane(containerUnderTitleBar, issueToShow, issuePC);
    }

    @Override
    public Pane buildImageViewComponent(StackPane containerUnderTitleBar, byte[] imageToView) {
        return new ImageViewerPane(containerUnderTitleBar, imageToView);
    }

    @Override
    public Pane buildAllLabelsComponent(StackPane containerUnderTitleBar) {

        LabelManagementPC labelManagementPC = new LabelManagementPC(this, new UserLabelController(new UserLabelDAO_REST(new MyHTTPClient())));

        try {
            labelManagementPC.setLabels();

            return new AllLabelsPane(
                    labelManagementPC,
                    containerUnderTitleBar
            );
        }
        catch (RetrieveLabelsException e) {
            closeWindow(containerUnderTitleBar.getParent().getScene().getWindow());
            labelManagementPC.showErrorAlert(e.getMessage(), e.getTechnicalMessage());
        }

        return new Pane();
    }

    @Override
    public void navigateToLoginPage() {
        AuthenticationController authenticationController = new AuthenticationController(new AuthenticationDAO_REST(new MyHTTPClient()));
        LoginPC loginPC = new LoginPC(authenticationController, new NavigationManager_JavaFX());
        LoginView loginView = new LoginView(loginPC);

        loginView.show();
    }
}