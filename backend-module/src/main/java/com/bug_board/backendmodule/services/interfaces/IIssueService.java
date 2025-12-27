package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IIssueService {
    public IssueSummaryDTO publishNewIssueToProject(@NotNull @NotBlank String usernamePrincipal,
                                                    @NotNull @Min(1) Integer idProject,
                                                    @NotNull @Valid IssueCreationDTO issueToAdd);
    public List<IssueSummaryDTO> getIssuesOfAUser(@NotNull @NotBlank String username,
                                                  @NotNull @Valid IssueFiltersDTO filters);
    public List<IssueSummaryDTO> getIssuesOfAProject(@NotNull @Min(1) Integer idProject,
                                                     @NotNull @Valid IssueFiltersDTO filters);
    public List<IssueImageDTO> getImagesOfAIssue(@NotNull @Min(1) Integer projectId,
                                                 @NotNull @Min(1) Integer issueId);
    public List<IssueImageDTO> getImagesOfAIssue(@NotNull @NotBlank String username,
                                                 @NotNull @Min(1) Integer issueId);

}
