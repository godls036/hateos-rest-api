package com.inflearn_rest_api_study.rest_api_with_spring.events;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {

}