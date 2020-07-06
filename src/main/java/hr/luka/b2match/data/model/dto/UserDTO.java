package hr.luka.b2match.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String surname;

    private String username;

    private String email;

    private String password;

    private Long organizationId;

}
