package hr.luka.b2match.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.luka.b2match.data.model.dto.EventDTO;
import hr.luka.b2match.data.repository.EventRepository;
import hr.luka.b2match.service.UserService;
import hr.luka.b2match.service.mapper.EventMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    @Autowired
    EventMapper eventMapper;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void shouldCreateEventTest() throws Exception {
        EventDTO eventDTO = EventDTO.builder()
                .name("Sajam Knjiga")
                .location("Zagrebacki velesajam")
                .startTime(LocalDateTime.of(2020,10,15,12,0))
                .endTime(LocalDateTime.of(2020,10,18,18,0))
                .build();

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isOk());
    }

    @Test()
    public void shouldCreateEventTestTimeException() throws Exception {
        EventDTO eventDTO = EventDTO.builder()
                .name("Sajam Knjiga")
                .location("Zagrebacki velesajam")
                .startTime(LocalDateTime.of(2020,10,15,12,0))
                .endTime(LocalDateTime.of(2020,10,14,18,0))
                .build();

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isBadRequest());
    }



}
