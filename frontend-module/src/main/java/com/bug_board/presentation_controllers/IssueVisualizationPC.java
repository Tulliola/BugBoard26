package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ProjectIssueController;
import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IssueVisualizationPC {
    private UserIssueController userIssueController;
    private ProjectIssueController projectIssueController;
    private final INavigationManager navigationManager;
    private IssueVisualizationView issueView;

    private List<IssueSummaryDTO> issueList;

    private static final int PAGE_SIZE = 3;

    public IssueVisualizationPC(UserIssueController userIssueController,
                                INavigationManager navigationManager) {
        this.userIssueController = userIssueController;
        this.navigationManager = navigationManager;
    }

    public IssueVisualizationPC(ProjectIssueController projectIssueController,
                                INavigationManager navigationManager) {
        this.projectIssueController = projectIssueController;
        this.navigationManager = navigationManager;
    }

    public void setView(IssueVisualizationView issueView) {
        this.issueView = issueView;
    }

    public void onGoBackButtonClicked() {
        navigationManager.closeWindow(this.issueView);
        try {
            navigationManager.navigateToHomePage();
        }
        catch (RetrieveProjectException e) {
            showIssuesRetrievalError();
        }
    }

    public void showIssuesRetrievalError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Server error");
        alert.setHeaderText("Couldn't retrieve the issues.");
        alert.setContentText("Server's not responding. You can visualize the issue page, but it will be empty.");
        alert.showAndWait();
    }

    public List<IssueSummaryDTO> getIssuesOfAPage(int currentPage) {

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, issueList.size());

        return issueList.subList(start, end);
    }

    public int getNumberOfPagesOfAGivenSublist(List<IssueSummaryDTO> list) {
        return Math.ceilDiv(list.size(), PAGE_SIZE);
    }

    public int getTotalNumberOfPages() {
        return Math.ceilDiv(this.issueList.size(), PAGE_SIZE);
    }

    public List<String> getTipologyStrings() {
        return Arrays.stream(IssueTipology.values()).map(IssueTipology::toString).toList();
    }

    public List<String> getPriorityStrings() {
        return Arrays.stream(IssuePriority.values()).map(IssuePriority::toString).toList();
    }

    public List<String> getStateStrings() {
        return Arrays.stream(IssueState.values()).map(IssueState::toString).toList();
    }

    public void setIssueList(List<IssueSummaryDTO> issueList) {
        this.issueList = issueList;
    }

    public List<IssueSummaryDTO> getAllIssues() {
        return this.issueList;
    }
}
