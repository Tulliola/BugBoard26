package com.bug_board.dto;

import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreationDTO {
    @NotNull(message = "Title must not be null.")
    @NotBlank(message = "Title must be specified.")
    @Size(max = 40)
    private String title;

    @NotNull(message = "Description must not be null.")
    @NotBlank(message = "Description must be specified.")
    private String description;

    @NotNull(message = "Tipology must not be null.")
    private IssueTipology tipology;

    @NotNull(message = "Priority must not be null")
    private IssuePriority priority;

    @Size(max = 3, message = "Issue must have at most 3 images")
    private List<@Size(max = 5 * 1024 * 1024) byte[]> images;
    private final IssueState state = IssueState.TODO;

    @NotNull(message = "Project must not be null.")
    @Min(value = 1, message = "IdProject must be greater than 0.")
    private Integer idProject;

    @Size(max = 3, message = "Issue must have at most 3 labels")
    private List<@Min(value = 1, message = "idLabel must be greater than 0.")
                 @NotNull(message = "idLabel must not be null.") Integer> idLabels;
}
