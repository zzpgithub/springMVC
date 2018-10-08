package com.tw.relife.story6;

import com.tw.relife.*;
import com.tw.relife.controller.ControllerTest;
import com.tw.relife.controller.ExceptionController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiActionsControllerTest {

    @Test
    void should_test_add_more_than_one_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerTest.class)
                .addController(ExceptionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/test_story6", RelifeMethod.GET));

        assertEquals(201, response.getStatus());
        assertEquals("test", response.getContent());

        assertEquals(200, response2.getStatus());
        assertEquals("test_story6", response2.getContent());
    }

    @Test
    void should_test_add_same_path_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerTest.class)
                .addController(ExceptionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/test", RelifeMethod.GET));

        assertEquals(201, response.getStatus());
        assertEquals("test", response.getContent());
        assertEquals(response.getStatus(), response2.getStatus());
        assertEquals(response.getContent(), response2.getContent());
    }

    @Test
    void should_test_throw_when_add_same_controller() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(ControllerTest.class)
                        .addController(ControllerTest.class)
                        .build());
    }
}
