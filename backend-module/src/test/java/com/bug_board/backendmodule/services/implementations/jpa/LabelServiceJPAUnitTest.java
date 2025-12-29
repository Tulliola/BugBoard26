package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.backendmodule.entity.RegularUser;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import com.bug_board.backendmodule.services.implementations.jpa_implementations.LabelServiceJPA;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabelServiceJPAUnitTest {
    @Mock
    ILabelRepository mockLabelRepository;

    @Mock
    IUserService mockUserService;

    @InjectMocks
    LabelServiceJPA serviceToTest;

    private Label createValidMockLabelInDatabase(User mockUser, Integer idLabel) {
        Label validMockLabel = new Label();
        validMockLabel.setCreator(mockUser);
        validMockLabel.setIdLabel(idLabel);
        validMockLabel.setName("Mock label name");
        validMockLabel.setDescription("Mock label description");
        validMockLabel.setColor("#FFAA00");

        return validMockLabel;
    }

    private User createValidMockUserInDatabase(String usernamePrincipal) {
        User validMockUser = new RegularUser();
        validMockUser.setUsername(usernamePrincipal);

        return validMockUser;
    }

    @Test
    public void modifyingALabelAddingNoDescriptionAndANewColorShouldReturnSuccess() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;

        User mockUser = this.createValidMockUserInDatabase(usernamePrincipal);
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, idLabel);

        LabelModifyingDTO labelToModify = new LabelModifyingDTO(
                idLabel,
                mockLabel.getName(),
                null,
                "#FFAA00"
        );


        when(mockLabelRepository.getLabelById(idLabel)).thenReturn(mockLabel);
        when(mockLabelRepository.updateLabel(any(Label.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        when(mockUserService.findUserByUsername(usernamePrincipal)).thenReturn(mockUser);

        /* Act */
        LabelSummaryDTO result = serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelToModify);

        /* Assert */
        assertAll(
                () -> assertEquals("#FFAA00", result.getColor()),
                () -> assertNull(result.getDescription()),
                () -> assertEquals(idLabel, result.getIdLabel()),
                () -> assertEquals(mockLabel.getName(), result.getName())
        );
    }

    @Test
    public void modifyingALabelAddingValidDescriptionAndNoColorShouldReturnSuccess() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;

        User mockUser = this.createValidMockUserInDatabase(usernamePrincipal);
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, idLabel);

        LabelModifyingDTO labelToModify = new LabelModifyingDTO(
                idLabel,
                mockLabel.getName(),
                "Very short modified mock label description",
                null
        );


        when(mockLabelRepository.getLabelById(idLabel)).thenReturn(mockLabel);
        when(mockLabelRepository.updateLabel(any(Label.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        when(mockUserService.findUserByUsername(usernamePrincipal)).thenReturn(mockUser);

        /* Act */
        LabelSummaryDTO result = serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelToModify);

        /* Assert */
        assertAll(
                () -> assertEquals("#FFFFFF", result.getColor()),
                () -> assertEquals("Very short modified mock label description", result.getDescription()),
                () -> assertEquals(idLabel, result.getIdLabel()),
                () -> assertEquals(mockLabel.getName(), result.getName())
        );
    }

    @Test
    public void whenIdLabelAsParameterAndIdLabelInLabelToModifyDontMatchShouldThrowBadRequestException() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;
        LabelModifyingDTO labelModified = new LabelModifyingDTO();
        labelModified.setIdLabel(25);

        /* Act + assert */
        assertThrows(BadRequestException.class, () -> serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    public void whenIdLabelDoesntCorrespondToAnyLabelInDBShouldThrowResourceNotFoundException() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                idLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );

        when(mockLabelRepository.getLabelById(idLabel)).thenReturn(null);

        /* Act + assert */
        assertThrows(ResourceNotFoundException.class, () -> serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    public void whenUsernamePrincipalDoesntMatchWithLabelCreatorUsernameShouldThrowAccessDeniedException() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                idLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );
        User mockUser = this.createValidMockUserInDatabase("Tulliola");
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, idLabel);

        when(mockLabelRepository.getLabelById(idLabel)).thenReturn(mockLabel);

        /* Act + assert */
        assertThrows(AccessDeniedException.class, () -> serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    public void whenUsernamePrincipalDoesntCorrespondToAnyUserInDBShouldThrowResourceNotFoundException() {
        /* Arrange */
        final String usernamePrincipal = "justantxnio";
        final Integer idLabel = 100;
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                idLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );
        User mockUser = this.createValidMockUserInDatabase("justantxnio");
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, idLabel);

        when(mockLabelRepository.getLabelById(idLabel)).thenReturn(mockLabel);
        when(mockUserService.findUserByUsername(usernamePrincipal)).thenReturn(null);

        /* Act + assert */
        assertThrows(ResourceNotFoundException.class, () -> serviceToTest.modifyPersonalLabel(usernamePrincipal, idLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }
}
