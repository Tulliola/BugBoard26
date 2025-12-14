package com.bug_board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelSummaryDTO {
    private String name;
    private String color;
    private String description;
    private Integer idLabel;
}
