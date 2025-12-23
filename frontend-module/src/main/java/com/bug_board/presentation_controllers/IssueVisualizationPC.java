package com.bug_board.presentation_controllers;

import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class IssueVisualizationPC extends ServerDependantPresentationController {

    protected IssueVisualizationView issueView;

    protected List<IssueSummaryDTO> issueList;
    protected static final int PAGE_SIZE = 3;

    protected List<String> tipologyFilters = new ArrayList<>();
    protected List<String> priorityFilters = new ArrayList<>();
    protected List<String> stateFilters = new ArrayList<>();

    public IssueVisualizationPC(INavigationManager navigationManager){
        super(navigationManager);
    }

    public void setView(IssueVisualizationView issueView) {
        this.issueView = issueView;
    }

    public void onGoBackButtonClicked() {
        navigationManager.closeWindow(this.issueView);
        navigationManager.navigateToHomePage();
    }

    public List<IssueSummaryDTO> getIssuesOfAPage(int currentPage) {

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, issueList.size());

        return issueList.subList(start, end);
    }

    public int getNumberOfPages() {
        return Math.ceilDiv(this.issueList.size(), PAGE_SIZE);
    }

    public abstract void filterIssueList();

    public List<IssueTipology> getTipologies() {
        return Arrays.asList(IssueTipology.values());
    }

    public List<IssuePriority> getPriorities() {
        return Arrays.asList(IssuePriority.values());
    }

    public List<IssueState> getStates() {
        return Arrays.asList(IssueState.values());
    }

    public abstract List<IssueImageDTO> getAssociatedImagesOfAIssue(Integer idProject, Integer idIssue);

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

    public void showIssueSummaryPane(StackPane containerUnderTitleBar, IssueSummaryDTO issueToShow) {
        Pane issueSummaryOverlay = navigationManager.buildIssueSummaryComponent(containerUnderTitleBar, issueToShow, this);

        issueView.displayOverlayedContent(issueSummaryOverlay);
    }

    public void showImageViewerPane(StackPane containerUnderTitleBar, byte[] imageToView) {
        Pane imageViewOverlay = navigationManager.buildImageViewComponent(containerUnderTitleBar, imageToView);

        issueView.displayOverlayedContent(imageViewOverlay);
    }
}
