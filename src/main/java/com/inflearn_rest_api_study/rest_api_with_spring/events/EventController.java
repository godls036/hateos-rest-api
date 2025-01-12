package com.inflearn_rest_api_study.rest_api_with_spring.events;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
        EventEntity event = modelMapper.map(eventDto, EventEntity.class);
        EventEntity newEvent = eventRepository.save(event);
        URI createUri = WebMvcLinkBuilder.linkTo(EventController.class).slash(newEvent.getId().toString()).toUri();
        return ResponseEntity.created(createUri).body(newEvent);
    }
}
