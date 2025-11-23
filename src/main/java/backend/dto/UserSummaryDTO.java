package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryDTO {
    private String username;
    private Byte[] image;
}
