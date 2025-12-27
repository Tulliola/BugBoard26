package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.Project;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.dto.UserSummaryDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IProjectService {
    public List<ProjectSummaryDTO> getOverviewedProjects(@NotNull @NotBlank String username, String projectNameToFilter);
    public List<ProjectSummaryDTO> getWorkingOnProjects(@NotNull @NotBlank String username, String projectNameToFilter);
    public UserSummaryDTO assignCollaboratorToProject(@NotNull @Min(1) Integer idProject,
                                                      @NotNull @NotBlank String collaboratorUsername);
    public Project getProject(@NotNull @Min(1) Integer idProject);
}
