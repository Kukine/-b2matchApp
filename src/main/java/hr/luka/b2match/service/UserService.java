package hr.luka.b2match.service;

import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findUserById(Long userId);

    UserDTO getUserDTOById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO registerUser(UserDTO userDTO);

    List<UserDTO> getAllUsersByEvent(Long eventId);

    List<UserDTO> getAllUsersByMeeting(Long meetingId);

}
