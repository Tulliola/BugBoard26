package com.bug_board.dto;

import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreationDTO {
    @NotNull(message = "Title must not be null.")
    @NotBlank(message = "Title must be specified.")
    private String title;

    @NotNull(message = "Description must not be null.")
    @NotBlank(message = "Description must be specified.")
    private String description;

    @NotNull(message = "Tipology must not be null.")
    private IssueTipology tipology;
    private IssuePriority priority;
    private List<byte[]> images;
    private final IssueState state = IssueState.TODO;

    @NotNull(message = "Project must not be null.")
    @Min(value = 1, message = "IdProject must be greater than 0.")
    private Integer idProject;

    private List<@Min(value = 1, message = "idLabel must be greater than 0.")
                 @NotNull(message = "idLabel must not be null.") Integer> idLabels;
}
