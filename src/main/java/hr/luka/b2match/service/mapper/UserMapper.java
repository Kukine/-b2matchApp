package hr.luka.b2match.service.mapper;

import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements AbstractDTOMapper<User, UserDTO> {

    @Override
    public User fromDto(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

    }

    @Override
    public UserDTO fromEntity(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .organizationId(entity.getOrganization().getId())
                .build();
    }
}
