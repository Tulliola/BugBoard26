package com.bug_board.factories;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.enum_classes.UserRole;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class ProjectFactory {
    private static ProjectFactory projectFactory;
    private static UserProjectController projectController;

    private ProjectFactory() {

    }

    public static ProjectFactory getInstance() {
        if(projectFactory == null)
            projectFactory = new ProjectFactory();

        return projectFactory;
    }

    public ProjectFactory setProjectController(UserProjectController projectController) {
        ProjectFactory.projectController = projectController;
        return projectFactory;
    }

    public List<ProjectSummaryDTO> getProjectsOnBoardByRole(UserRole loggedUserRole)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException {
        if(projectController == null)
            throw new IllegalArgumentException("ProjectController still not set.");

        if(loggedUserRole.getRoleName().equals("ROLE_USER"))
            return projectController.getWorkingOnProjectsByUser(null);
        else if(loggedUserRole.getRoleName().equals("ROLE_ADMIN") || loggedUserRole.getRoleName().equals("ROLE_SUPERADMIN"))
            return projectController.getOverviewedProjectsByUser(null);
        else
            throw new IllegalArgumentException("Role specified not found.");
    }
}
