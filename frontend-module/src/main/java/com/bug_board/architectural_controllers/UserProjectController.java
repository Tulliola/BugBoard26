package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import java.util.List;

public class UserProjectController {
    private final IUserProjectDAO userProjectDAO;

    public UserProjectController(IUserProjectDAO userProjectDAO) {
        this.userProjectDAO = userProjectDAO;
    }

    public List<ProjectSummaryDTO> getOverviewedProjectsByUser() {
        return null;
    }
}
