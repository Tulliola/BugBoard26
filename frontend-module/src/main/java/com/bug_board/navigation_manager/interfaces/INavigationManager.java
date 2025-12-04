package com.bug_board.navigation_manager.interfaces;

import com.bug_board.dto.ProjectSummaryDTO;
import java.util.List;

public interface INavigationManager {
    void navigateToHomePageFromLogin();

    void closeWindow(Object windowToClose);
}
