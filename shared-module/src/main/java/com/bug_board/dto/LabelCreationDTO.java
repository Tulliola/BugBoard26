package com.bug_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelCreationDTO {
    @NotNull(message = "Creator must not be null.")
    @NotBlank(message = "Creator must be specified.")
    private String creator;

    @NotNull(message = "Name must not be null.")
    @NotBlank(message = "Name must be specified.")
    private String name;

    private String description;
    private String color;
}
