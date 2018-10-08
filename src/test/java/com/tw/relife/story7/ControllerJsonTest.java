package com.tw.relife.story7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.relife.*;
import com.tw.relife.controller.ControllerJson;
import com.tw.relife.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerJsonTest {
    @Test
    void should_test_return_json_when_return_type_not_relifeResponse() throws JsonProcessingException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerJson.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/json", RelifeMethod.GET));

        String expectedJson = new ObjectMapper().writeValueAsString(new Person("Jack", 1));
        assertEquals(200, response.getStatus());
        assertEquals(expectedJson, response.getContent());
        assertEquals("application/json", response.getContentType());
    }
}
