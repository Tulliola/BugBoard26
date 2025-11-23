package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserCreationDTO {
    private String  email;
    private String username;
    //private Role role
}
