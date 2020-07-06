package hr.luka.b2match.service.impl;

import hr.luka.b2match.data.model.Invitation;
import hr.luka.b2match.data.model.InvitationStatus;
import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.InvitationDTO;
import hr.luka.b2match.data.repository.InvitationRepository;
import hr.luka.b2match.service.InvitationService;
import hr.luka.b2match.service.MeetingService;
import hr.luka.b2match.service.UserService;
import hr.luka.b2match.service.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hr.luka.b2match.config.exception.ExceptionSuppliers.MEETING_SCHEDULE_EXCEPTION;
import static hr.luka.b2match.config.exception.ExceptionSuppliers.ENTITY_NOT_FOUND_EXCEPTION;
import static hr.luka.b2match.config.exception.ExceptionSuppliers.EVENT_PARTICIPATION_EXCEPTION;

@Service
public class InvitationServiceImpl implements InvitationService {

    private InvitationRepository invitationRepository;
    private MeetingService meetingService;
    private UserService userService;
    private InvitationMapper invitationMapper;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, MeetingService meetingService, UserService userService, InvitationMapper invitationMapper){
        this.invitationRepository = invitationRepository;
        this.meetingService = meetingService;
        this.userService = userService;
        this.invitationMapper = invitationMapper;
    }

    @Override
    public List<InvitationDTO> getAllInvitationsForRecipient(Long recipientId){
        User userById = this.userService.findUserById(recipientId);
        List<Invitation> allByRecipient = this.invitationRepository.findAllByRecipient(userById);
        return allByRecipient.stream().map(this.invitationMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<InvitationDTO> getAllInvitationsForSender(Long senderId){
        User userById = this.userService.findUserById(senderId);
        List<Invitation> allByRecipient = this.invitationRepository.findAllBySender(userById);
        return allByRecipient.stream().map(this.invitationMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<InvitationDTO> getAllInvitations(){
        return this.invitationRepository.findAll().stream().map(this.invitationMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void sendInvitation(Long meetingId , Long senderId, Long recipientId){
        Invitation invitation = new Invitation();
        Meeting meetingFromInvitation = this.meetingService.getMeetingById(meetingId);
        User sender = this.userService.findUserById(senderId);
        User recipient = this.userService.findUserById(recipientId);
        if(!meetingFromInvitation.getEvent().getParticipants().contains(recipient)){
            throw EVENT_PARTICIPATION_EXCEPTION.get();
        }
        for(Meeting meeting : this.meetingService.getAllMeetingsForUser(recipientId)){
            if(meeting.isConcurrent(meetingFromInvitation)){
                throw MEETING_SCHEDULE_EXCEPTION.get();
            }
        }

        invitation.setMeeting(meetingFromInvitation);
        invitation.setSender(sender);
        invitation.setRecipient(recipient);
        invitation.setStatus(InvitationStatus.PENDING.getValue());

        this.invitationRepository.save(invitation);
    }

    @Override
    public void resolveInvitation(Long invitationId, boolean accepted){
        Invitation invitation = this.invitationRepository.findById(invitationId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
        if(accepted){
            invitation.setStatus(InvitationStatus.ACCEPTED.getValue());
            this.meetingService.addUserToMeeting(invitation.getMeeting().getId(),invitation.getRecipient().getId());
        } else {
            invitation.setStatus(InvitationStatus.DECLINED.getValue());
        }
        Invitation save = this.invitationRepository.save(invitation);

        this.meetingService.attemptMeetingSchedule(save.getMeeting().getId());
    }
}
