package com.inflearn_rest_api_study.rest_api_with_spring.events;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void builder() {
        EventEntity event = EventEntity.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")

                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        EventEntity event = new EventEntity();
        String testName = "Inflearn Spring REST API";
        String testDescription = "REST API development with Spring";
        event.setName(testName);
        event.setDescription(testDescription);
        assertThat(event.getName()).isEqualTo(testName);
        assertThat(event.getDescription()).isEqualTo(testDescription);
    }
}
