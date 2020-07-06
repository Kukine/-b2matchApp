package hr.luka.b2match.controller;

import hr.luka.b2match.data.model.dto.InvitationDTO;
import hr.luka.b2match.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService){
        this.invitationService = invitationService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendInvitation(
            @RequestParam(name = "meetingId") Long meetingId,
            @RequestParam(name = "senderId") Long senderId,
            @RequestParam(name = "recipientId") Long recipientId){
        try{
            this.invitationService.sendInvitation(meetingId,senderId,recipientId);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resolve")
    public ResponseEntity<?> resolveInvitation(
            @RequestParam(name = "invitationId") Long invitationId,
            @RequestParam(name = "accepted") boolean accepted){
        this.invitationService.resolveInvitation(invitationId, accepted);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipient")
    public ResponseEntity<List<InvitationDTO>> getAllInvitationsForRecipient(@RequestParam(name = "recipientId") Long recipientId){
        List<InvitationDTO> allInvitationsForRecipient = this.invitationService.getAllInvitationsForRecipient(recipientId);
        return ResponseEntity.ok(allInvitationsForRecipient);
    }

    @GetMapping("/sender")
    public ResponseEntity<List<InvitationDTO>> getAllInvitationsForSender(@RequestParam(name = "senderId") Long senderId){
        List<InvitationDTO> allInvitationsForRecipient = this.invitationService.getAllInvitationsForSender(senderId);
        return ResponseEntity.ok(allInvitationsForRecipient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvitationDTO>> getAllInvitations(){
        List<InvitationDTO> allInvitationsForRecipient = this.invitationService.getAllInvitations();
        return ResponseEntity.ok(allInvitationsForRecipient);
    }
}
