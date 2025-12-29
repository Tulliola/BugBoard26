package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.entity.*;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.implementations.jpa_implementations.IssueServiceJPA;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueTipology;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueServiceJPAUnitTest {
    @Mock
    private IIssueRepository issueRepository;

    @Mock
    private IProjectService projectService;

    @Mock
    private ILabelService labelService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private IssueServiceJPA issueServiceJPA;

    @Test
    public void publishIssueWithNoImagesAndThreeLabelsShouldReturnSuccess(){
        final String usernamePrincipal = "Tulliola";
        final Integer idProject = 1;
        List<Integer> mockLabelsIds = List.of(4, 5, 6);
        IssueCreationDTO issueCreationDTO = new IssueCreationDTO(
                "Issue title",
                "Issue description",
                IssueTipology.BUG,
                IssuePriority.NO_PRIORITY,
                null,
                idProject,
                mockLabelsIds
        );

        User mockUser = createUserMock(usernamePrincipal);
        Project mockProject = createProjectMock(idProject);
        List<Label> mockLabels = mockLabelsIds.stream()
                .map(this::createLabelMock)
                .toList();


        when(projectService.getProject(idProject)).thenReturn(mockProject);
        when(userService.getUser(usernamePrincipal)).thenReturn(mockUser);
        when(issueRepository.createANewIssueToProject(any(Issue.class))).thenAnswer(
                invocation ->  invocation.getArgument(0)
        );
        mockLabels.forEach(label ->
                when(labelService.getLabel(label.getIdLabel())).thenReturn(label)
        );

        IssueSummaryDTO result = issueServiceJPA.publishNewIssueToProject(usernamePrincipal, idProject, issueCreationDTO);

        assertThat(result)
                .usingRecursiveComparison().ignoringFields( "idIssue", "creationDate", "resolutionDate", "labels", "creatorName", "creatorBioPic")
                .isEqualTo(issueCreationDTO);

        assertAll(
                () -> assertNull(result.getResolutionDate()),
                () -> {
                    LocalDate resultCreationDate = result.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    assertEquals(LocalDate.now(),  resultCreationDate);
                },
                () -> assertEquals(usernamePrincipal, result.getCreatorName()),
                () -> assertEquals(issueCreationDTO.getIdLabels().size(), result.getLabels().size()),
                () -> {
                    List<Integer> actualLabelsIdInserted = result.getLabels().stream().map(LabelSummaryDTO::getIdLabel).toList();

                    assertTrue(actualLabelsIdInserted.containsAll(issueCreationDTO.getIdLabels()));
                }
        );

        verify(issueRepository).createANewIssueToProject(any(Issue.class));
    }

    @Test
    public void publishIssueWithImagesAndNoLabelsShouldReturnSuccess(){
        final String usernamePrincipal = "Tulliola";
        final Integer idProject = 1;
        List<byte[]> mockedImages = List.of(new byte[500], new byte[500], new byte[500]);

        IssueCreationDTO issueCreationDTO = new IssueCreationDTO(
                "Issue title",
                "Issue description",
                IssueTipology.DOCUMENTATION,
                IssuePriority.HIGH_PRIORITY,
                mockedImages,
                idProject,
                null
        );

        User mockUser = createUserMock(usernamePrincipal);
        Project mockProject = createProjectMock(idProject);

        when(projectService.getProject(idProject)).thenReturn(mockProject);
        when(userService.getUser(usernamePrincipal)).thenReturn(mockUser);
        when(issueRepository.createANewIssueToProject(any(Issue.class))).thenAnswer(
                invocation ->  invocation.getArgument(0)
        );

        IssueSummaryDTO result = issueServiceJPA.publishNewIssueToProject(usernamePrincipal, idProject, issueCreationDTO);

        assertThat(result)
                .usingRecursiveComparison().ignoringFields( "idIssue", "creationDate", "resolutionDate", "labels", "creatorName", "creatorBioPic")
                .isEqualTo(issueCreationDTO);

        assertAll(
                () -> assertNull(result.getResolutionDate()),
                () -> {
                    LocalDate resultCreationDate = result.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    assertEquals(LocalDate.now(),  resultCreationDate);
                },
                () -> assertEquals(usernamePrincipal, result.getCreatorName()),
                () -> assertTrue(result.getLabels().isEmpty())
        );

        verify(issueRepository).createANewIssueToProject(any(Issue.class));
    }

    private Label createLabelMock(Integer lableId) {
        Label mockLabel = new Label();

        mockLabel.setIdLabel(lableId);

        return mockLabel;
    }

    private Project createProjectMock(Integer projectId) {
        Project projectMock = new Project();
        projectMock.setIdProject(projectId);

        return projectMock;
    }

    private User createUserMock(String usernamePrincipal) {
        User userMock = new RegularUser();
        userMock.setUsername(usernamePrincipal);

        return userMock;
    }
}
