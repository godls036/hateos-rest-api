package com.inflearn_rest_api_study.rest_api_with_spring.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        public void createEvent() throws Exception {
                EventDto eventDto = EventDto.builder()
                                .name("Spring")
                                .description("REST API development with Spring")
                                .beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 23, 14, 21))
                                .closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 24, 14, 22))
                                .beginEventDateTime(LocalDateTime.of(2021, 11, 25, 14, 23))
                                .endEventDateTime(LocalDateTime.of(2021, 11, 26, 14, 24))
                                .basePrice(100)
                                .maxPrice(200)
                                .limitOfEnrollment(100)
                                .location("강남역")
                                .build();

                mockMvc.perform(post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaTypes.HAL_JSON)
                                .content(objectMapper.writeValueAsString(eventDto)))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("id").exists())
                                .andExpect(jsonPath("id").value(Matchers.not(10)))
                                .andExpect(jsonPath("free").value(Matchers.not(true)))
                                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                                .andExpect(header().exists(HttpHeaders.LOCATION))
                                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
        }

        @Test
        public void createEvent_BadRequest() throws Exception {
                EventEntity event = EventEntity.builder()
                                .id(10)
                                .name("Spring")
                                .description("REST API development with Spring")
                                .beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 23, 14, 21))
                                .closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 24, 14, 22))
                                .beginEventDateTime(LocalDateTime.of(2021, 11, 25, 14, 23))
                                .endEventDateTime(LocalDateTime.of(2021, 11, 26, 14, 24))
                                .basePrice(100)
                                .maxPrice(200)
                                .limitOfEnrollment(100)
                                .location("강남역")
                                .free(true)
                                .eventStatus(EventStatus.PUBLISHED)
                                .build();

                mockMvc.perform(post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaTypes.HAL_JSON)
                                .content(objectMapper.writeValueAsString(event)))
                                .andDo(print())
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createEvent_BadRequest_EmptyInput() throws Exception {
                EventDto eventDto = EventDto.builder().build();

                this.mockMvc.perform(post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(eventDto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createEvent_BadRequest_InvalidInput() throws Exception {
                EventDto eventDto = EventDto.builder()
                                .name("Spring")
                                .description("REST API development with Spring")
                                .beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 25, 14, 21))
                                .closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 24, 14, 22))
                                .beginEventDateTime(LocalDateTime.of(2021, 11, 25, 14, 23))
                                .endEventDateTime(LocalDateTime.of(2021, 11, 26, 14, 24))
                                .basePrice(10000)
                                .maxPrice(200)
                                .limitOfEnrollment(100)
                                .location("강남역")
                                .build();

                this.mockMvc.perform(post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(eventDto)))
                                .andExpect(status().isBadRequest());
        }
}
