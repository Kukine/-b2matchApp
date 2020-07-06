package hr.luka.b2match.controller;

import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.UserDTO;
import hr.luka.b2match.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok(this.userService.getUserDTOById(userId));
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(this.userService.registerUser(userDTO));
    }

    @GetMapping("/event")
    public ResponseEntity<List<UserDTO>> getAllUsersByEvent(@RequestParam(name = "eventId") Long eventId){
        return ResponseEntity.ok(this.userService.getAllUsersByEvent(eventId));
    }

    @GetMapping("/meeting")
    public ResponseEntity<List<UserDTO>> getAllUsersByMeeting(@RequestParam(name = "meetingId") Long meetingId){
        return ResponseEntity.ok(this.userService.getAllUsersByMeeting(meetingId));
    }


}
