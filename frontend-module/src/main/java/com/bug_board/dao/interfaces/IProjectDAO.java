package com.bug_board.dao.interfaces;

import com.bug_board.dto.UserSummaryDTO;

import java.util.List;

public interface IProjectDAO {
    public abstract void assignCollaboratorToProject(Integer idProject, String collaboratorUsername);
    public abstract List<UserSummaryDTO> getAddableUsersToProject(Integer idProject);
}
