package hr.luka.b2match.controller;

import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.dto.MeetingDTO;
import hr.luka.b2match.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService){
        this.meetingService = meetingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MeetingDTO>> getAllMeetings(){
        return ResponseEntity.ok(this.meetingService.getAllMeetings());
    }

    @GetMapping("/scheduled")
    public ResponseEntity<List<MeetingDTO>> getAllScheduledMeetings(){
        return ResponseEntity.ok(this.meetingService.getAllScheduledMeetings());
    }

    @GetMapping("")
    public ResponseEntity<MeetingDTO> getMeetingById(@RequestParam(name = "meetingId") Long meetingId){
        return ResponseEntity.ok(this.meetingService.getMeetingDTOById(meetingId));
    }

    @GetMapping("/event")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsFromEvent(@RequestParam(name = "eventId") Long eventId){
        return ResponseEntity.ok(this.meetingService.getAllMeetingsFromEvent(eventId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsForUser(@RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok(this.meetingService.getAllMeetingDTOsForUser(userId));
    }

    @PostMapping("")
    public ResponseEntity<?> createMeeting(@RequestBody MeetingDTO meetingDTO){
        try{
             return ResponseEntity.ok(this.meetingService.createMeeting(meetingDTO));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
