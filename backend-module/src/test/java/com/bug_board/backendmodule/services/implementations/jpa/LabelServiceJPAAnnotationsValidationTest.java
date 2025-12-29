package com.bug_board.backendmodule.services.implementations.jpa;

import com.bug_board.backendmodule.repositories.interfaces.ILabelRepository;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.dto.LabelModifyingDTO;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")
@WithMockUser(username = "justantxnio", roles = {"USER"})
public class LabelServiceJPAAnnotationsValidationTest {
    @Autowired
    private ILabelService labelServiceToTest;

    @MockitoBean
    private ILabelRepository mockLabelRepository;

    @MockitoBean
    private IUserService mockUserService;

    private final String validUsernamePrincipal = "justantxnio";
    private final Integer validIdLabel = 1;
    private LabelModifyingDTO validLabelModifyingDTO() {
        LabelModifyingDTO validLabel = new LabelModifyingDTO();
        validLabel.setIdLabel(1);
        validLabel.setName("Mock label");
        validLabel.setDescription("Mock description");
        validLabel.setColor("#FFFFFF");

        return validLabel;
    }

    @Test
    public void whenUsernameIsNullShouldThrowConstraintViolationException() {

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(null, validIdLabel, validLabelModifyingDTO())
        );
    }

    @Test
    public void whenUsernameIsBlankShouldThrowConstraintViolationException() {
        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel("    ", validIdLabel, validLabelModifyingDTO())
        );
    }

    @Test
    public void whenIdLabelAsParameterIsNullShouldThrowConstraintViolationException() {
        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, null, validLabelModifyingDTO())
        );
    }

    @Test
    public void whenIdLabelAsParameterIsLessThanOneShouldThrowConstraintViolationException() {
        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, 0, validLabelModifyingDTO())
        );
    }

    @Test
    public void whenLabelToModifyIsNullShouldThrowConstraintViolationException() {
        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, null)
        );
    }

    @Test
    public void whenIdLabelInLabelToModifyIsNullShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();
        invalidDTO.setIdLabel(null);

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenIdLabelInLabelToModifyIsLessThenOneShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();
        invalidDTO.setIdLabel(-5);

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenNameInLabelToModifyIsNullShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();
        invalidDTO.setName(null);

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenNameInLabelToModifyIsBlankShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();
        invalidDTO.setName(" ");

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenNameInLabelToModifyHasMoreThan50CharsShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();

        invalidDTO.setName("012345678901234567890123456789012345678901234567890123456789");

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenDescriptionInLabelToModifyHasMoreThan200CharsShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();

        invalidDTO.setDescription("012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789" +
                "012345678901234567890123456789");

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }

    @Test
    public void whenColorInLabelToModifyDoesNotMatchWithHexadecimalPatternShouldThrowConstraintViolationException() {
        LabelModifyingDTO invalidDTO = this.validLabelModifyingDTO();
        invalidDTO.setColor("#AABBFZ");

        assertThrows(ConstraintViolationException.class,
                () -> labelServiceToTest.modifyPersonalLabel(validUsernamePrincipal, validIdLabel, invalidDTO)
        );
    }
}
