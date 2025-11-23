package backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class IssueSummaryDTO {
    private Integer idIssue;
    private String title;
    private String description;
    //private State state;
    //private Tipology tipology;
    private List<Byte[]> images;
    private Date creationDate;
    private Date resolutionDate;
}
