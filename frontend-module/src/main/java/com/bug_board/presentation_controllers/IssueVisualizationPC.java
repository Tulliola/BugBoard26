package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ProjectIssueController;
import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
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

    private List<String> tipologyFilters = new ArrayList<>();
    private List<String> priorityFilters = new ArrayList<>();
    private List<String> stateFilters = new ArrayList<>();

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

    public List<IssueSummaryDTO> extractFirstPageIssues(List<IssueSummaryDTO> initialList) {
        return initialList.subList(0, Math.min(PAGE_SIZE, initialList.size()));
    }

    public int getNumberOfPagesOfAGivenSublist(List<IssueSummaryDTO> list) {
        return Math.ceilDiv(list.size(), PAGE_SIZE);
    }

    public int getTotalNumberOfPages() {
        return Math.ceilDiv(this.issueList.size(), PAGE_SIZE);
    }

    public List<IssueSummaryDTO> getFilteredIssueList() {
        IssueFiltersDTO filters = new IssueFiltersDTO();
        filters.setIssueStates(this.stateFilters);
        filters.setIssueTipologies(this.tipologyFilters);
        filters.setIssuePriorities(this.priorityFilters);

        try {
            if (issueView.getIdProject() != null)
                issueList = projectIssueController.getProjectIssues(issueView.getIdProject(), filters);
            else
                issueList = userIssueController.getPersonalIssues(filters);
            return issueList;
        }
        catch(RetrieveIssuesException exc) {
            this.showIssuesRetrievalError();
            return null;
        }
    }

    public List<IssueTipology> getTipologies() {
        return Arrays.asList(IssueTipology.values());
    }

    public List<IssuePriority> getPriorities() {
        return Arrays.asList(IssuePriority.values());
    }

    public List<IssueState> getStates() {
        return Arrays.asList(IssueState.values());
    }

    public void addTipologyFilter(String newTipologyFilter) {
        this.tipologyFilters.add(newTipologyFilter);
    }

    public void removeTipologyFilter(String tipologyFilterToRemove) {
        this.tipologyFilters.remove(tipologyFilterToRemove);
    }

    public void addPriorityFilter(String newPriorityFilter) {
        this.priorityFilters.add(newPriorityFilter);
    }

    public void removePriorityFilter(String priorityFilterToRemove) {
        this.priorityFilters.remove(priorityFilterToRemove);
    }

    public void addStateFilter(String newStateFilter) {
        this.stateFilters.add(newStateFilter);
    }

    public void removeStateFilter(String stateFilterToRemove) {
        this.stateFilters.remove(stateFilterToRemove);
    }

    public void setIssueList(List<IssueSummaryDTO> issueList) {
        this.issueList = issueList;
    }

    public List<IssueSummaryDTO> getAllIssues() {
        return this.issueList;
    }
}
