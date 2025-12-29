package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.implementations.jpa_implementations.IssueServiceJPA;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IssueServiceJPAUnitTest {
    @Mock
    private IIssueRepository issueRepository;

    @Mock
    private IProjectService projectService;

    @Mock
    private ILabelService labelService;

    @InjectMocks
    private IssueServiceJPA issueServiceJPA;

    @Test
    public void testValidPublishOfAnIssue(){
        
    }
}
