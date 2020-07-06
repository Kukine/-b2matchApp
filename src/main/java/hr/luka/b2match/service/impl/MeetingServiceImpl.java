package hr.luka.b2match.service.impl;

import hr.luka.b2match.data.model.*;
import hr.luka.b2match.data.model.dto.MeetingDTO;
import hr.luka.b2match.data.repository.MeetingRepository;
import hr.luka.b2match.service.EventService;
import hr.luka.b2match.service.MeetingService;
import hr.luka.b2match.service.UserService;
import hr.luka.b2match.service.mapper.MeetingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hr.luka.b2match.config.exception.ExceptionSuppliers.*;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserService userService;
    private final EventService eventService;
    private final MeetingMapper meetingMapper;

    public MeetingServiceImpl(MeetingRepository meetingRepository, UserService userService, EventService eventService, MeetingMapper meetingMapper){
        this.meetingRepository = meetingRepository;
        this.userService = userService;
        this.eventService = eventService;
        this.meetingMapper = meetingMapper;
    }

    @Override
    public MeetingDTO createMeeting(MeetingDTO meetingDTO) {
        User organizer = this.userService.findUserById(meetingDTO.getOrganizerId());
        Event eventById = this.eventService.getEventById(meetingDTO.getEventId());
        if(meetingDTO.getEndTime().isBefore(meetingDTO.getStartTime())){
            throw TIME_INCONSISTENT_EXCEPTION.get();
        }
        if(!eventById.getParticipants().contains(organizer)){
            throw EVENT_PARTICIPATION_EXCEPTION.get();
        }
        List<Meeting> currentMeetings = this.meetingRepository.findAllByParticipants(organizer);
        for(Meeting curr : currentMeetings){
            if(curr.isConcurrent(meetingDTO) && !curr.getStatus().equals(MeetingStatus.CANCELED.getValue())){
                throw MEETING_SCHEDULE_EXCEPTION.get();
            }
        }
        Meeting meeting = this.meetingMapper.fromDto(meetingDTO);
        meeting.setOrganizer(organizer);
        meeting.setEvent(eventById);
        meeting.setStatus(MeetingStatus.PENDING.getValue());
        meeting.getParticipants().add(organizer);

        return this.meetingMapper.fromEntity(this.meetingRepository.save(meeting));
    }

    @Override
    public boolean addUserToMeeting(Long meetingId, Long userId){
        Meeting meeting = this.meetingRepository.findById(meetingId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
        User participant = this.userService.findUserById(userId);
        List<Meeting> allByParticipants = this.meetingRepository.findAllByParticipants(participant);
        for(Meeting participantMeeting : allByParticipants){
            if(meeting.isConcurrent(participantMeeting)){
                return false;
            }
        }
        meeting.getParticipants().add(participant);
        this.meetingRepository.save(meeting);
        return true;
    }

    @Override
    public Meeting getMeetingById(Long meetingId){
        return this.meetingRepository.findById(meetingId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
    }

    @Override
    public MeetingDTO getMeetingDTOById(Long meetingId) {
        return this.meetingMapper.fromEntity(getMeetingById(meetingId));
    }

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return this.meetingRepository.findAll().stream().map(this.meetingMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllScheduledMeetings(){
        return getAllMeetings().stream().filter(m -> m.getStatus().equals(MeetingStatus.SCHEDULED.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllMeetingsFromEvent(Long eventId) {
        Event eventById = this.eventService.getEventById(eventId);
        return this.meetingRepository.findAllByEvent(eventById).stream().map(this.meetingMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllMeetingDTOsForUser(Long userId){
        return getAllMeetingsForUser(userId).stream().map(this.meetingMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<Meeting> getAllMeetingsForUser(Long userId) {
        User userById = this.userService.findUserById(userId);
        return this.meetingRepository.findAllByParticipants(userById);
    }

    @Override
    public boolean attemptMeetingSchedule(Long meetingId){
        Meeting meeting = this.meetingRepository.findById(meetingId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);

        boolean schedule = true;
        for(Invitation invitation : meeting.getInvitations()){
            if(invitation.getStatus().equals(InvitationStatus.DECLINED.getValue())){
                meeting.setStatus(MeetingStatus.CANCELED.getValue());
                this.meetingRepository.save(meeting);
                return false;

            }
            if(invitation.getStatus().equals(InvitationStatus.PENDING.getValue())){
                schedule = false;
                break;
            }
        }
        if(schedule) {
            meeting.setStatus(MeetingStatus.SCHEDULED.getValue());
            this.meetingRepository.save(meeting);
        }
        return schedule;
    }

}
