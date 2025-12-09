package com.bug_board.backendmodule.mappers;

import com.bug_board.backendmodule.entity.Label;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;

import java.util.List;

public class LabelMapper {
    public static LabelSummaryDTO toDTO(Label labelToMap) {
        LabelSummaryDTO mappedLabel = new LabelSummaryDTO();

        mappedLabel.setName(labelToMap.getName());
        mappedLabel.setColor(labelToMap.getColor());

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
