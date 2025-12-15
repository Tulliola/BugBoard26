package com.bug_board.backendmodule.mappers;

import com.bug_board.dto.IssueImageDTO;

import java.util.List;

public class IssueImageMapper {
    private IssueImageMapper() {}

    public static IssueImageDTO toDTO(byte[] image) {
        IssueImageDTO issueImageDTO = new IssueImageDTO();

        issueImageDTO.setBinaryFile(image);

        return issueImageDTO;
    }

    public static List<IssueImageDTO> toDTOS (List<byte[]> images) {
        return  images.stream()
                .map(IssueImageMapper::toDTO)
                .toList();
    }
}
