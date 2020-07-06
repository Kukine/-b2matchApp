package hr.luka.b2match.controller;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.dto.EventDTO;
import hr.luka.b2match.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAllEvents(){
        return ResponseEntity.ok(this.eventService.getAllEvents());
    }

    @GetMapping("")
    public ResponseEntity<EventDTO> getEventByID(@RequestParam("eventId") Long eventId){
        return ResponseEntity.ok(this.eventService.getEventDTOById(eventId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> getAllUpcomingEvents(){
        return ResponseEntity.ok(this.eventService.getAllUpcomingEvents());
    }

    @PostMapping("")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO){
        return ResponseEntity.ok(this.eventService.createEvent(eventDTO));
    }

    @PostMapping("/user")
    public ResponseEntity<EventDTO> addUserToEvent(
            @RequestParam(name = "eventId") Long eventId,
            @RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok(this.eventService.addUserToEvent(eventId,userId));
    }


}
