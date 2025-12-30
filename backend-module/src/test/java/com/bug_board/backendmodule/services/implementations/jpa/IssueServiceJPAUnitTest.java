package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.entity.*;
import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
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
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private final String validUsernamePrincipal = "Tulliola";
    private final Integer validProjectId = 1;

    private IssueCreationDTO validIssueCreationDTO() {
        return new IssueCreationDTO(
                "Issue title",
                "Issue description",
                IssueTipology.BUG,
                IssuePriority.NO_PRIORITY,
                Collections.emptyList(),
                validProjectId,
                Collections.emptyList()
        );
    }

    @Test
    public void publishIssueWithNoImagesAndThreeLabelsShouldReturnSuccess() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<Integer> labelsIds = List.of(4, 5, 6);
        issueCreationDTO.setIdLabels(labelsIds);

        User mockUser = createUserMock(validUsernamePrincipal);
        Project mockProject = createProjectMock(validProjectId);
        List<Label> mockLabels = labelsIds.stream()
                .map(this::createLabelMock)
                .toList();


        when(projectService.getProject(validProjectId)).thenReturn(mockProject);
        when(userService.getUser(validUsernamePrincipal)).thenReturn(mockUser);
        when(issueRepository.createANewIssueToProject(any(Issue.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        mockLabels.forEach(label ->
                when(labelService.getLabel(label.getIdLabel())).thenReturn(label)
        );

        IssueSummaryDTO result = issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO);

        assertThat(result)
                .usingRecursiveComparison().ignoringFields("idIssue", "creationDate", "resolutionDate", "labels", "creatorName", "creatorBioPic")
                .isEqualTo(issueCreationDTO);

        assertAll(
                () -> assertNull(result.getResolutionDate()),
                () -> {
                    LocalDate resultCreationDate = result.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    assertEquals(LocalDate.now(), resultCreationDate);
                },
                () -> assertEquals(validUsernamePrincipal, result.getCreatorName()),
                () -> assertEquals(issueCreationDTO.getIdLabels().size(), result.getLabels().size()),
                () -> {
                    List<Integer> actualLabelsIdInserted = result.getLabels().stream().map(LabelSummaryDTO::getIdLabel).toList();

                    assertTrue(actualLabelsIdInserted.containsAll(issueCreationDTO.getIdLabels()));
                }
        );

        verify(issueRepository).createANewIssueToProject(any(Issue.class));
    }

    @Test
    public void publishIssueWithImagesAndNoLabelsShouldReturnSuccess() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<byte[]> mockedImages = List.of(new byte[500], new byte[500], new byte[500]);
        issueCreationDTO.setImages(mockedImages);
        issueCreationDTO.setIdLabels(null);

        User mockUser = createUserMock(validUsernamePrincipal);
        Project mockProject = createProjectMock(validProjectId);

        when(projectService.getProject(validProjectId)).thenReturn(mockProject);
        when(userService.getUser(validUsernamePrincipal)).thenReturn(mockUser);
        when(issueRepository.createANewIssueToProject(any(Issue.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        IssueSummaryDTO result = issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO);

        assertThat(result)
                .usingRecursiveComparison().ignoringFields("idIssue", "creationDate", "resolutionDate", "labels", "creatorName", "creatorBioPic")
                .isEqualTo(issueCreationDTO);

        assertAll(
                () -> assertNull(result.getResolutionDate()),
                () -> {
                    LocalDate resultCreationDate = result.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    assertEquals(LocalDate.now(), resultCreationDate);
                },
                () -> assertEquals(validUsernamePrincipal, result.getCreatorName()),
                () -> assertTrue(result.getLabels().isEmpty())
        );

        verify(issueRepository).createANewIssueToProject(any(Issue.class));
    }

    private Label createLabelMock(Integer labelId) {
        Label mockLabel = new Label();

        mockLabel.setIdLabel(labelId);

        return mockLabel;
    }

    private Label createLabelMockWithCreator(Integer labelId, User mockCreator) {
        Label mockLabel = new Label();
        mockLabel.setIdLabel(labelId);

        mockLabel.setCreator(mockCreator);

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

    //Inizio testing white box

    @Test
    public void nonConsistentProjectIdsShouldThrowBadRequestException() {
        IssueCreationDTO nonConsistentIssueCreationDTO = validIssueCreationDTO();
        final Integer differentIdProject = 2;
        nonConsistentIssueCreationDTO.setIdProject(differentIdProject);

        assertThrows(BadRequestException.class, () -> issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, nonConsistentIssueCreationDTO));
    }

    @Test
    public void nonConsistentLabelCreatorAndUsernamePrincipalShouldThrowAccessDeniedException() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<Integer> nonConsistentLabelIds = List.of(4, 5, 6);
        issueCreationDTO.setIdLabels(nonConsistentLabelIds);
        final String differentUsernamePrincipal = "justantxnio";

        User differentMockUser = createUserMock(differentUsernamePrincipal);
        List<Label> mockLabels = new ArrayList<>();
        for (Integer labelId : issueCreationDTO.getIdLabels())
            mockLabels.add(createLabelMockWithCreator(labelId, differentMockUser));

        Project mockProject = createProjectMock(validProjectId);
        when(projectService.getProject(validProjectId)).thenReturn(mockProject);

        when(labelService.getLabel(mockLabels.get(0).getIdLabel())).thenReturn(mockLabels.get(0));

        assertThrows(AccessDeniedException.class, () -> issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO));

        verify(issueRepository, never()).createANewIssueToProject(any(Issue.class));
    }

    @Test
    public void nonNullAndConsistentCreatorShouldReturnSuccess() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<Integer> mockLabelsIds = List.of(4, 5, 6);
        issueCreationDTO.setIdLabels(mockLabelsIds);

        User mockUser = createUserMock(validUsernamePrincipal);
        Project mockProject = createProjectMock(validProjectId);
        List<Label> mockLabels = new ArrayList<>();
        for (Integer labelId : issueCreationDTO.getIdLabels())
            mockLabels.add(createLabelMockWithCreator(labelId, mockUser));


        when(projectService.getProject(validProjectId)).thenReturn(mockProject);
        when(userService.getUser(validUsernamePrincipal)).thenReturn(mockUser);
        when(issueRepository.createANewIssueToProject(any(Issue.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        mockLabels.forEach(label ->
                when(labelService.getLabel(label.getIdLabel())).thenReturn(label)
        );

        IssueSummaryDTO result = issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO);

        assertThat(result)
                .usingRecursiveComparison().ignoringFields("idIssue", "creationDate", "resolutionDate", "labels", "creatorName", "creatorBioPic")
                .isEqualTo(issueCreationDTO);

        assertAll(
                () -> assertNull(result.getResolutionDate()),
                () -> {
                    LocalDate resultCreationDate = result.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    assertEquals(LocalDate.now(), resultCreationDate);
                },
                () -> assertEquals(validUsernamePrincipal, result.getCreatorName()),
                () -> assertEquals(issueCreationDTO.getIdLabels().size(), result.getLabels().size()),
                () -> {
                    List<Integer> actualLabelsIdInserted = result.getLabels().stream().map(LabelSummaryDTO::getIdLabel).toList();

                    assertTrue(actualLabelsIdInserted.containsAll(issueCreationDTO.getIdLabels()));
                }
        );

        verify(issueRepository).createANewIssueToProject(any(Issue.class));
    }

    @Test
    public void nullRetrievedLabelShouldThrowResourceNotFoundException() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<Integer> labelsMocks = List.of(4, 5, 6);
        issueCreationDTO.setIdLabels(labelsMocks);

        User differentMockUser = createUserMock(validUsernamePrincipal);
        List<Label> mockLabels = new ArrayList<>();
        for (Integer labelId : issueCreationDTO.getIdLabels())
            mockLabels.add(createLabelMockWithCreator(labelId, differentMockUser));

        Project mockProject = createProjectMock(validProjectId);
        when(projectService.getProject(validProjectId)).thenReturn(mockProject);

        when(labelService.getLabel(mockLabels.get(0).getIdLabel())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO));

        verify(issueRepository, never()).createANewIssueToProject(any(Issue.class));
    }

    @Test
    public void nullRetrievedProjectShouldThrowResourceNotFoundException() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        List<Integer> labelsMocks = List.of(4, 5);
        issueCreationDTO.setIdLabels(labelsMocks);

        User differentMockUser = createUserMock(validUsernamePrincipal);
        List<Label> mockLabels = new ArrayList<>();
        for (Integer labelId : issueCreationDTO.getIdLabels())
            mockLabels.add(createLabelMockWithCreator(labelId, differentMockUser));

        when(projectService.getProject(validProjectId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> issueServiceJPA.publishNewIssueToProject(validUsernamePrincipal, validProjectId, issueCreationDTO));

        verify(issueRepository, never()).createANewIssueToProject(any(Issue.class));
    }
}