package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSummaryDTO {
    private Integer idProject;
    private String title;
    private String description;
    private Byte[] image;

    private String projectCreator;
}
