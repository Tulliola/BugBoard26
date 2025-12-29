package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueTipology;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@WithMockUser(username = "Tulliola", roles = {"USER"})
@ActiveProfiles("dev")
public class IssueServiceJPAAnnotationsValidationTest {
    @Autowired
    private IIssueService issueService;

    @MockitoBean
    private IIssueRepository issueRepository;

    @MockitoBean
    private IProjectService projectService;

    @MockitoBean
    private ILabelService labelService;

    @MockitoBean
    private IUserService userService;

    private final String validUsername = "Tulliola";
    private final Integer validIdProject = 1;
    private IssueCreationDTO validIssueCreationDTO(){
        IssueCreationDTO issueCreationDTO = new IssueCreationDTO();

        issueCreationDTO.setTitle("Issue title");
        issueCreationDTO.setDescription("Issue description");
        issueCreationDTO.setTipology(IssueTipology.BUG);
        issueCreationDTO.setPriority(IssuePriority.NO_PRIORITY);
        issueCreationDTO.setImages(null);
        issueCreationDTO.setIdLabels(null);
        issueCreationDTO.setIdProject(validIdProject);

        return issueCreationDTO;
    }

    /*
    CE coperte:
    CE1 NV
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullUsernameShouldThrowConstraintViolationException() {

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(null, validIdProject, validIssueCreationDTO());
        });

    }

     /*
    CE coperte:
    CE2 NV
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.17 V
    CE8.21 V
     */
    @Test
    public void emptyUsernameShouldThrowConstraintViolationException() {
        IssueCreationDTO issueCreationDTO = validIssueCreationDTO();
        issueCreationDTO.setImages(List.of(new byte[5 * 1023 * 1023], new byte[5 * 1023 * 1023], new byte[5 * 1023 * 1023]));
        issueCreationDTO.setIdLabels(List.of(1, 2, 3));

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject("", validIdProject, validIssueCreationDTO());
        });
    }

    /*
    CE coperte:
    CE3 V
    CE4 NV
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullProjectShouldThrowConstraintViolationException() {

        assertThrows(ConstraintViolationException.class, () -> {
           issueService.publishNewIssueToProject(validUsername, null, validIssueCreationDTO());
        });
    }

    /*
    CE coperte:
    CE3 V
    CE5 NV
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void projectWithIdLessThanOrEqualToZeroShouldThrowConstraintViolationException() {

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 0, validIssueCreationDTO());
        });

    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE7 NV
     */
    @Test
    public void nullIssueCreationDTOShouldThrowConstraintViolationException() {
        assertThrows(ConstraintViolationException.class, () -> {
           issueService.publishNewIssueToProject(validUsername, 1, null);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.1 NV
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullIssueTitleShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setTitle(null);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.2 NV
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void emptyIssueTitleShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setTitle("");

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.3 NV
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void issueTitleLongerThanFortyCharsShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        String invalidTitleLength = "";
        for(int i = 0; i < 41; i++){
            invalidTitleLength += "a";
        }
        invalidIssueCreationDTO.setTitle(invalidTitleLength);

        assertThrows(ConstraintViolationException.class, () -> {
           issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.5 NV
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullIssueDescriptionShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setDescription(null);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.6 NV
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void emptyIssueDescriptionShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setDescription("");

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.8 NV
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullIssueTipologyShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setTipology(null);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.10 NV
    CE8.14 V
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullIssuePriorityShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setPriority(null);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.12 NV
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void nullIssueProjectShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setIdProject(null);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.13 NV
    CE8.15 V
    CE8.19 V
     */
    @Test
    public void issueProjectWithIdLessThanOrEqualToZeroShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setIdProject(-15);

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.16 NV
    CE8.19 V
     */
    @Test
    public void issueWithMoreThanThreeAssociatedImagesShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setImages(List.of(new byte[1], new byte[1], new byte[1], new byte[1]));

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.18 NV
    CE8.19 V
     */
    @Test
    public void issueAtLeastOneImageLargerThanFiveMegabytesShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setImages(List.of(new byte[6 * 1024 * 1024], new byte[1], new byte[1], new byte[1]));

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.20 NV
     */
    @Test
    public void issueWithMoreThanThreeAssociatedLabelsShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setIdLabels(List.of(1, 2, 34 ,55));

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }

    /*
    CE coperte:
    CE3 V
    CE6 V
    CE8 V
    CE8.4 V
    CE8.7 V
    CE8.9 V
    CE8.11 V
    CE8.14 V
    CE8.15 V
    CE8.22 NV
     */
    @Test
    public void issueAssociatedLabelsWithIdLessThanOrEqualToZeroShouldThrowConstraintViolationException() {
        IssueCreationDTO invalidIssueCreationDTO = validIssueCreationDTO();
        invalidIssueCreationDTO.setIdLabels(List.of(1, 2, 34 , -5));

        assertThrows(ConstraintViolationException.class, () -> {
            issueService.publishNewIssueToProject(validUsername, 1, invalidIssueCreationDTO);
        });
    }
}
