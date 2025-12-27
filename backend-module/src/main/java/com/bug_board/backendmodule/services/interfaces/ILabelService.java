package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ILabelService {
    public LabelSummaryDTO createPersonalLabel(@NotNull @NotBlank String usernamePrincipal,
                                               @NotNull @Valid LabelCreationDTO labelToCreate);
    public void deletePersonalLabel(@NotNull @NotBlank String usernamePrincipal,
                                    @NotNull @Min(1) Integer idLabel);
    public LabelSummaryDTO modifyPersonalLabel(@NotNull @NotBlank String usernamePrincipal,
                                               @NotNull @Min(1) Integer idLabel,
                                               @NotNull @Valid LabelModifyingDTO labelToModify);
    public List<LabelSummaryDTO> getPersonalLabels(@NotNull @NotBlank String username);
    public Label getLabel(@NotNull @Min(1) Integer idLabel);
}
