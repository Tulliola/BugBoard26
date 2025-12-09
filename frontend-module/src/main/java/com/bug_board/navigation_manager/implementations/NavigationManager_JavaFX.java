package com.bug_board.navigation_manager.implementations;

import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.architectural_controllers.UserRegistrationController;
import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.*;
import com.bug_board.dao.interfaces.IUserIssueDAO;
import com.bug_board.exceptions.architectural_controllers.RetrievePersonalIssuesException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.factories.ProjectFactory;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.gui.panes.ReportIssuePane;
import com.bug_board.gui.panes.UserRegistrationFormPane;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.presentation_controllers.IssueVisualizationPC;
import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.presentation_controllers.ReportIssuePC;
import com.bug_board.presentation_controllers.UserRegistrationPC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.gui.views.HomePageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NavigationManager_JavaFX implements INavigationManager {

    @Override
    public void navigateToHomePage(){
        UserProjectController projectController = new UserProjectController(new UserProjectDAO_REST(new MyHTTPClient()));
        HomePagePC homePagePC = new HomePagePC(projectController, this);

        try {
            HomePageView homePageView = new HomePageView(
                    homePagePC,
                    ProjectFactory.getInstance()
                            .setProjectController(projectController)
                            .getProjectsOnBoardByRole(SessionManager.getInstance().getRole())
            );
            homePagePC.setView(homePageView);

            homePageView.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void navigateToViewIssues()
            throws RetrievePersonalIssuesException {
        IUserIssueDAO userIssueDAO = new UserIssueDAO_REST(new MyHTTPClient());
        UserIssueController issueController = new UserIssueController(userIssueDAO);
        IssueVisualizationPC issuePC = new IssueVisualizationPC(issueController, this);

        try {
            IssueVisualizationView issueView = new IssueVisualizationView(
                    userIssueDAO.getPersonalIssues()
            );
            issuePC.setView(issueView);

            issueView.show();
        }
        catch (ErrorHTTPResponseException | HTTPSendException | BadConversionToDTOException throwables) {
            throw new RetrievePersonalIssuesException(throwables.getMessage());
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

        LabelManagementPC labelPC = new LabelManagementPC(labelController);

        LabelCreationFormPane labelCreationFormPane = new LabelCreationFormPane(labelPC, parentContainer);

        labelPC.setPane(labelCreationFormPane);

        return labelCreationFormPane;
    }

    @Override
    public Pane buildRegisterUserComponent(StackPane parentContainer, HomePagePC parentPC) {
        //TODO creiamo un oggetto MyHTTPClient per riutilizzo?
        UserRegistrationController userRegistrationController = new UserRegistrationController(new UserDAO_REST(new MyHTTPClient()), new EmailSenderDAO_REST(new MyHTTPClient()));

        UserRegistrationPC userRegistrationPC = new UserRegistrationPC(userRegistrationController);

        UserRegistrationFormPane userRegistrationPane = new UserRegistrationFormPane(parentContainer, userRegistrationPC);

        userRegistrationPC.setPane(userRegistrationPane);

        return userRegistrationPane;
    }

    @Override
    public Pane buildReportIssueComponent(StackPane parentContainer, HomePagePC homePagePC) {
        ReportIssueController reportIssueController = new ReportIssueController(new UserIssueDAO_REST(new MyHTTPClient()));

        ReportIssuePC reportIssuePC = new ReportIssuePC(reportIssueController);

        ReportIssuePane reportIssuePane = new ReportIssuePane(parentContainer, homePagePC);

        reportIssuePC.setPane(reportIssuePane);

        return reportIssuePane;
    }
}
