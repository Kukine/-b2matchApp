package hr.luka.b2match.service;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {

    MeetingDTO createMeeting(MeetingDTO meetingDTO);

    boolean addUserToMeeting(Long meetingId, Long userId);

    Meeting getMeetingById(Long meetingId);

    MeetingDTO getMeetingDTOById(Long meetingId);

    List<MeetingDTO> getAllMeetings();

    List<MeetingDTO> getAllScheduledMeetings();

    List<MeetingDTO> getAllMeetingsFromEvent(Long eventId);

    List<MeetingDTO> getAllMeetingDTOsForUser(Long userId);

    List<Meeting> getAllMeetingsForUser(Long userId);

    boolean attemptMeetingSchedule(Long meetingId);


}
