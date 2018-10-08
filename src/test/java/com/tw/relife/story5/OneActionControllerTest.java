package com.tw.relife.story5;

import com.tw.relife.*;
import com.tw.relife.controller.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OneActionControllerTest {
    @Test
    void should_test_AC1() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse responseGET = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, responseGET.getStatus());
        assertEquals("Hello from /path", responseGET.getContent());
    }

    @Test
    void should_test_AC2_input_null() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                                                            .addController(null).build());
    }

    @Test
    void should_test_AC3_throw_exception_input_abstract_or_interface() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                                                            .addController(AbstractController.class));
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                                                            .addController(InterfaceController.class));
    }

    @Test
    void should_test_AC4_without_relifeController() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithoutRelife.class));
    }

    @Test
    void should_test_AC5_method_contain_two_params() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerTwoParam.class));
    }

    @Test
    void should_test_AC5_method_another_type_param() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerAnotherType.class));
    }

    @Test
    void should_test_multiply_actions() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(MultiActionController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertEquals("Hello from", response.getContent());

        RelifeResponse response2= app.process(new RelifeRequest("/path_two", RelifeMethod.GET));
        assertEquals(403, response2.getStatus());
        assertEquals("Hello from", response2.getContent());
    }

    @Test
    void should_test_same_actions_in_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(SameActionController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response1 = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(response1.getStatus(), response2.getStatus());
        assertEquals(response1.getContent(), response2.getContent());
    }

    @Test
    void should_return_status_when_controller_throw_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionController.class).build();

        RelifeApp app = new RelifeApp(handler);
        assertEquals(408, app.process(new RelifeRequest("/path", RelifeMethod.GET)).getStatus());
    }
    @Test
    void should_test_controller_return_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/null", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertNull(response.getContent());
        assertNull(response.getContentType());
    }

    @Test
    void should_test_action_throw_common_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/run_time", RelifeMethod.GET));

        assertEquals(500, response.getStatus());
    }

}
