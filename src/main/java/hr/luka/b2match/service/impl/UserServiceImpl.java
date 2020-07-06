package hr.luka.b2match.service.impl;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.Organization;
import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.UserDTO;
import hr.luka.b2match.data.repository.UserRepository;
import hr.luka.b2match.service.EventService;
import hr.luka.b2match.service.MeetingService;
import hr.luka.b2match.service.OrganizationService;
import hr.luka.b2match.service.UserService;
import hr.luka.b2match.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hr.luka.b2match.config.exception.ExceptionSuppliers.ENTITY_NOT_FOUND_EXCEPTION;
import static hr.luka.b2match.config.exception.ExceptionSuppliers.EXISTS_BY_EMAIL_EXCEPTION;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;
    private final EventService eventService;
    private final MeetingService meetingService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrganizationService organizationService, UserMapper userMapper, PasswordEncoder passwordEncoder, @Lazy EventService eventService, @Lazy MeetingService meetingService){
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.meetingService = meetingService;
        this.eventService = eventService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        return this.userRepository.findAll().stream().map(this.userMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public User findUserById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
    }

    @Override
    public UserDTO getUserDTOById(Long userId) {
        return this.userMapper.fromEntity(findUserById(userId));
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = this.userMapper.fromDto(userDTO);
        Organization organizationById = this.organizationService.getOrganizationById(userDTO.getOrganizationId());
        if(this.userRepository.existsByEmail(user.getEmail())){
            throw EXISTS_BY_EMAIL_EXCEPTION.get();
        }
        user.setOrganization(organizationById);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userMapper.fromEntity(this.userRepository.save(user));
    }


    @Override
    public List<UserDTO> getAllUsersByEvent(Long eventId){
        Event event = this.eventService.getEventById(eventId);
        return event.getParticipants().stream().map(this.userMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByMeeting(Long meetingId){
        Meeting meeting = this.meetingService.getMeetingById(meetingId);
        return meeting.getParticipants().stream().map(this.userMapper::fromEntity).collect(Collectors.toList());
    }
}
