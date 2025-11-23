package backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class LabelModifyingDTO {
    private Integer idLabel;
    private String name;
    private String description;
    private Color color;
}
