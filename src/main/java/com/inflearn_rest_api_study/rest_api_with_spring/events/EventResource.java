package com.inflearn_rest_api_study.rest_api_with_spring.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Arrays;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class EventResource extends EntityModel<EventEntity> {

    public EventResource(EventEntity event, Link... links) {
        super(event, Arrays.asList(links));
        add(linkTo(EventController.class).slash(event.getId().toString()).withSelfRel());
    }
}
