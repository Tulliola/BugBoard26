package com.bug_board.bugboard26.backend.mappers;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;

import java.util.List;

public class ProjectMapper {
    public static ProjectSummaryDTO toProjectSummaryDTO(Project projectToMap) {
        ProjectSummaryDTO projectSummaryDTO = new ProjectSummaryDTO();

        projectSummaryDTO.setIdProject(projectToMap.getIdProject());
        projectSummaryDTO.setTitle(projectToMap.getTitle());
        projectSummaryDTO.setDescription(projectToMap.getDescription());
        projectSummaryDTO.setImage(projectToMap.getImage());
        projectSummaryDTO.setProjectCreator(projectToMap.getCreator().getUsername());

        return projectSummaryDTO;
    }

    public static List<ProjectSummaryDTO> toProjectSummaryDTOS(List<Project> projects) {
        return projects
                .stream()
                .map(ProjectMapper::toProjectSummaryDTO)
                .toList();
    }
}
