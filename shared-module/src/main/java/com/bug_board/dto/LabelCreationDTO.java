package com.bug_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 50)
    private String name;

    @Size(max = 200)
    private String description;

    @Pattern(regexp = "^#[a-fA-F0-9]{6}$", message = "If specified, color must be defined in hexadecimal format.")
    private String color;
}
