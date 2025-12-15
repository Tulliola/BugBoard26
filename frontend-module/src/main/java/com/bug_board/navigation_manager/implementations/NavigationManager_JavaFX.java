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
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.factories.ProjectFactory;
import com.bug_board.gui.panes.*;
import com.bug_board.gui.views.IssueVisualizationView;
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
    public void navigateToHomePage() throws RetrieveProjectException {
        UserProjectController projectController = new UserProjectController(new UserProjectDAO_REST(new MyHTTPClient()));
        HomePagePC homePagePC = new HomePagePC(projectController, this);

        List<ProjectSummaryDTO> projectsList;

        try {
            projectsList = ProjectFactory.getInstance()
                    .setProjectController(projectController)
                    .getProjectsOnBoardByRole(SessionManager.getInstance().getRole());
        }
        catch (RetrieveProjectException exc) {
            homePagePC.showProjectsRetrievalError();
            projectsList = new ArrayList<>();
        }

        HomePageView homePageView = new HomePageView(
                homePagePC,
                projectsList
        );

        homePageView.show();
    }

    @Override
    public void navigateToViewPersonalIssues() {
        UserIssueController issueController = new UserIssueController(new UserIssueDAO_REST(new MyHTTPClient()));
        IssueVisualizationPC issuePC = new PersonalIssueVisualizationPC(this, issueController);

        List<IssueSummaryDTO> personalIssues = null;

        try {
            personalIssues = issueController.getPersonalIssues(null);
        }
        catch (RetrieveIssuesException e) {
            issuePC.showIssuesRetrievalError("Server's not responding. You can visualize the issue page, but it will be empty.");
            personalIssues = new ArrayList<>();
        }
        finally {
            issuePC.setIssueList(personalIssues);
        }

        IssueVisualizationView issueView = new IssueVisualizationView(
                issuePC,
                "Issues reported by you"
        );

        issueView.show();
    }

    @Override
    public void navigateToViewProjectIssues(Integer idProject, String projectName) {
        ProjectIssueController projectIssueController = new ProjectIssueController(new ProjectIssueDAO_REST(new MyHTTPClient()));
        IssueVisualizationPC issuePC = new ProjectIssueVisualizationPC(this, projectIssueController);

        List<IssueSummaryDTO> projectIssues = null;

        try {
             projectIssues = projectIssueController.getProjectIssues(idProject, null);
        }
        catch (RetrieveIssuesException e) {
            issuePC.showIssuesRetrievalError("Server's not responding. You can visualize the issue page, but it will be empty.");
            projectIssues = new ArrayList<>();
        }
        finally{
            issuePC.setIssueList(projectIssues);
        }

        IssueVisualizationView issueView = new IssueVisualizationView(
                issuePC,
                "Project \"" + projectName + "\" \'s issues",
                idProject
        );

        issueView.show();
    }

    @Override
    public void closeWindow(Object windowToClose){
        if(windowToClose instanceof Stage)
            ((Stage) windowToClose).close();
    }

    @Override
    public Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC) {
        UserLabelController labelController = new UserLabelController(new UserLabelDAO_REST(new MyHTTPClient()));

        LabelManagementPC labelPC = new LabelManagementPC(labelController);

        LabelCreationFormPane labelCreationFormPane = new LabelCreationFormPane(labelPC, parentContainer);

        return labelCreationFormPane;
    }

    @Override
    public Pane buildRegisterUserComponent(StackPane parentContainer, HomePagePC parentPC) {
        UserRegistrationController userRegistrationController = new UserRegistrationController(new UserDAO_REST(new MyHTTPClient()), new EmailSenderDAO_REST(new MyHTTPClient()));

        UserRegistrationPC userRegistrationPC = new UserRegistrationPC(userRegistrationController);

        UserRegistrationFormPane userRegistrationPane = new UserRegistrationFormPane(parentContainer, userRegistrationPC);

        userRegistrationPC.setPane(userRegistrationPane);

        return userRegistrationPane;
    }

    @Override
    public Pane buildReportIssueComponent(StackPane parentContainer, int projectToReport, HomePagePC homePagePC) {
        ReportIssueController reportIssueController = new ReportIssueController(new ProjectIssueDAO_REST(new MyHTTPClient()), new UserLabelDAO_REST(new MyHTTPClient()));

        ReportIssuePC reportIssuePC = new ReportIssuePC(reportIssueController, projectToReport);

        ReportIssuePane reportIssuePane = new ReportIssuePane(parentContainer, reportIssuePC);

        return reportIssuePane;
    }

    @Override
    public Pane buildIssueSummaryComponent(StackPane containerUnderTitleBar, IssueSummaryDTO issueToShow, IssueVisualizationPC issuePC) {
        IssueSummaryPane issueSummaryPane = new IssueSummaryPane(containerUnderTitleBar, issueToShow, issuePC);

        return issueSummaryPane;
    }

    @Override
    public Pane buildImageViewComponent(StackPane containerUnderTitleBar, byte[] imageToView) {
        return new ImageViewerPane(containerUnderTitleBar, imageToView);
    }
}
