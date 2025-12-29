package com.bug_board.backendmodule.service.implementations.jpa;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.backendmodule.entity.RegularUser;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.mappers.LabelMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabelServiceJPAUnitTest {
    @Mock
    static ILabelRepository mockLabelRepository;

    @Mock
    static IUserService mockUserService;

    @InjectMocks
    static LabelServiceJPA serviceToTest;

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
}
