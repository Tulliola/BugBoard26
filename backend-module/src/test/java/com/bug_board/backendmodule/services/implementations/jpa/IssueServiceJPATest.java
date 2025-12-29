package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.implementations.jpa_implementations.IssueServiceJPA;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class IssueServiceJPATest {
    @Mock
    private IIssueRepository issueRepository;

    @Mock
    private IProjectService projectService;

    @Mock
    private ILabelService labelService;

    @InjectMocks
    private IssueServiceJPA issueServiceJPA;

}
