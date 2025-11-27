package com.bug_board.bugboard26.backend.mappers;

import com.bug_board.bugboard26.backend.entity.Label;
import com.bug_board.bugboard26.dto.LabelCreationDTO;
import com.bug_board.bugboard26.dto.LabelModifyingDTO;
import com.bug_board.bugboard26.dto.LabelSummaryDTO;

import java.util.List;

public class LabelMapper {
    public static LabelSummaryDTO toDTO(Label labelToMap) {
        LabelSummaryDTO mappedLabel = new LabelSummaryDTO();

        mappedLabel.setName(labelToMap.getName());
        mappedLabel.setColor(labelToMap.getColor());
        mappedLabel.setCreator(labelToMap.getCreatorUsername());

        return mappedLabel;
    }

    public static Label labelModifyingDTOtoLabel(LabelModifyingDTO labelToModify) {
        Label mappedLabel = new Label();

        mappedLabel.setIdLabel(labelToModify.getIdLabel());
        mappedLabel.setName(labelToModify.getName());
        mappedLabel.setColor(labelToModify.getColor());
        mappedLabel.setDescription(labelToModify.getDescription());

        return mappedLabel;
    }

    public static Label labelCreationDTOtoLabel(LabelCreationDTO labelToMap) {
        Label mappedLabel = new Label();

        mappedLabel.setName(labelToMap.getName());
        mappedLabel.setColor(labelToMap.getColor());
        mappedLabel.setDescription(labelToMap.getDescription());

        return mappedLabel;
    }

    public static List<LabelSummaryDTO> toLabelSummaryDTOS(List<Label> labelList) {
        return labelList
                .stream()
                .map(LabelMapper::toDTO)
                .toList();
    }
}
