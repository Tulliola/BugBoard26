package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssueImagesException;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import java.util.List;

public class PersonalIssueVisualizationPC extends IssueVisualizationPC{
    private UserIssueController userIssueController;

    public PersonalIssueVisualizationPC(INavigationManager navigationManager,
                                        UserIssueController userIssueController) {
        super(navigationManager);
        this.userIssueController = userIssueController;
    }

    @Override
    public List<IssueSummaryDTO> getFilteredIssueList() {
        IssueFiltersDTO filters = new IssueFiltersDTO();
        filters.setIssueStates(this.stateFilters);
        filters.setIssueTipologies(this.tipologyFilters);
        filters.setIssuePriorities(this.priorityFilters);

        try {
            if (issueView.getIdProject() == null)
                issueList = userIssueController.getPersonalIssues(filters);
            return issueList;
        }
        catch(RetrieveIssuesException exc) {
            this.showIssuesRetrievalError("Server's not responding. You can visualize the issue page, but it will be empty.");
            return null;
        }
    }

    @Override
    public List<IssueImageDTO> getAssociatedImagesOfAIssue(Integer idProject, Integer idIssue) {
        try {
            return userIssueController.getAllIssueImages(idIssue);
        }
        catch (RetrieveIssueImagesException e) {
            this.showIssuesRetrievalError("Couldn't retrieve the images. Please, try later.");
            return null;
        }
    }
}
