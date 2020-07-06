package hr.luka.b2match.service;

import hr.luka.b2match.data.model.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {

    void sendInvitation(Long meetingId , Long senderId, Long recipientId);

    void resolveInvitation(Long invitationId, boolean accepted);

    List<InvitationDTO> getAllInvitationsForRecipient(Long recipientId);

    List<InvitationDTO> getAllInvitationsForSender(Long senderId);

    List<InvitationDTO> getAllInvitations();
}
