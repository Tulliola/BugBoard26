package com.bug_board.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelModifyingDTO {
    @NotNull(message = "Label must not be null.")
    @Min(value = 1, message = "Label must have an identifier greater or equal than 1")
    private Integer idLabel;

    @NotNull(message = "Name must not be null.")
    @NotBlank(message = "Name must be specified.")
    @Size(max = 50)
    private String name;

    @Size(max = 200)
    private String description;

    @Pattern(regexp = "^#[a-fA-F0-9]{6}$", message = "If specified, color must be defined in hexadecimal format.")
    private String color;
}
