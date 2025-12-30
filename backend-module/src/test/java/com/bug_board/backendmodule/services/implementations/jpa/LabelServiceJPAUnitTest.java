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
class LabelServiceJPAUnitTest {
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

    final String validUsernamePrincipal = "justantxnio";
    final Integer validIdLabel = 100;

    @Test
    void modifyingALabelAddingNoDescriptionAndANewColorShouldReturnSuccess() {
        /* Arrange */
        User mockUser = this.createValidMockUserInDatabase(validUsernamePrincipal);
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, validIdLabel);

        LabelModifyingDTO labelToModify = new LabelModifyingDTO(
                validIdLabel,
                mockLabel.getName(),
                null,
                "#FFAA00"
        );


        when(mockLabelRepository.getLabelById(validIdLabel)).thenReturn(mockLabel);
        when(mockLabelRepository.updateLabel(any(Label.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        when(mockUserService.findUserByUsername(validUsernamePrincipal)).thenReturn(mockUser);

        /* Act */
        LabelSummaryDTO result = serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelToModify);

        /* Assert */
        assertAll(
                () -> assertEquals("#FFAA00", result.getColor()),
                () -> assertNull(result.getDescription()),
                () -> assertEquals(validIdLabel, result.getIdLabel()),
                () -> assertEquals(mockLabel.getName(), result.getName())
        );
    }

    @Test
    void modifyingALabelAddingValidDescriptionAndNoColorShouldReturnSuccess() {
        /* Arrange */
        User mockUser = this.createValidMockUserInDatabase(validUsernamePrincipal);
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, validIdLabel);

        LabelModifyingDTO labelToModify = new LabelModifyingDTO(
                validIdLabel,
                mockLabel.getName(),
                "Very short modified mock label description",
                null
        );


        when(mockLabelRepository.getLabelById(validIdLabel)).thenReturn(mockLabel);
        when(mockLabelRepository.updateLabel(any(Label.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );
        when(mockUserService.findUserByUsername(validUsernamePrincipal)).thenReturn(mockUser);

        /* Act */
        LabelSummaryDTO result = serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelToModify);

        /* Assert */
        assertAll(
                () -> assertEquals("#FFFFFF", result.getColor()),
                () -> assertEquals("Very short modified mock label description", result.getDescription()),
                () -> assertEquals(validIdLabel, result.getIdLabel()),
                () -> assertEquals(mockLabel.getName(), result.getName())
        );
    }

    @Test
    void whenIdLabelAsParameterAndIdLabelInLabelToModifyDontMatchShouldThrowBadRequestException() {
        /* Arrange */
        LabelModifyingDTO labelModified = new LabelModifyingDTO();
        labelModified.setIdLabel(25);

        /* Act + assert */
        assertThrows(BadRequestException.class, () -> serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    void whenIdLabelDoesntCorrespondToAnyLabelInDBShouldThrowResourceNotFoundException() {
        /* Arrange */
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                validIdLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );

        when(mockLabelRepository.getLabelById(validIdLabel)).thenReturn(null);

        /* Act + assert */
        assertThrows(ResourceNotFoundException.class, () -> serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    void whenUsernamePrincipalDoesntMatchWithLabelCreatorUsernameShouldThrowAccessDeniedException() {
        /* Arrange */
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                validIdLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );
        User mockUser = this.createValidMockUserInDatabase("Tulliola");
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, validIdLabel);

        when(mockLabelRepository.getLabelById(validIdLabel)).thenReturn(mockLabel);

        /* Act + assert */
        assertThrows(AccessDeniedException.class, () -> serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }

    @Test
    void whenUsernamePrincipalDoesntCorrespondToAnyUserInDBShouldThrowResourceNotFoundException() {
        /* Arrange */
        LabelModifyingDTO labelModified = new LabelModifyingDTO(
                validIdLabel,
                "Mock label name",
                "Mock label description",
                "#FFFFFF"
        );
        User mockUser = this.createValidMockUserInDatabase("justantxnio");
        Label mockLabel = this.createValidMockLabelInDatabase(mockUser, validIdLabel);

        when(mockLabelRepository.getLabelById(validIdLabel)).thenReturn(mockLabel);
        when(mockUserService.findUserByUsername(validUsernamePrincipal)).thenReturn(null);

        /* Act + assert */
        assertThrows(ResourceNotFoundException.class, () -> serviceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, labelModified));

        verify(mockLabelRepository, never()).updateLabel(any(Label.class));
    }
}
