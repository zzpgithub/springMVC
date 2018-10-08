package com.tw.relife.story3;

import com.tw.relife.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RelifeMvcHandlerTest {

    @Test
    void should_test_path_not_correct() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/anotherPath",
                RelifeMethod.GET,
                request -> {
                    return new RelifeResponse(200, "Hello", "text/plain");
                }).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(404, response.getStatus());
    }

    @Test
    void should_test_method_not_correct() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.PUT,
                request -> {
                    return new RelifeResponse(200, "Hello", "text/plain");
                }).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(404, response.getStatus());
    }


    @Test
    void should_test_exception_when_path_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().addAction(
                null,
                RelifeMethod.GET,
                request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build());
    }
    @Test
    void should_test_exception_when_method_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().addAction(
                "/path",
                null,
                request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build());
    }    @Test
    void should_test_exception_when_handler_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                null)
                .build());
    }

    @Test
    void should_test() {
        RelifeAppHandler handler;
        handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertEquals("Hello", response.getContent());
    }


    @Test
    void should_test_request_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                request -> null).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
    }

    @Test
    void should_test_request_handler_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                request -> { throw new SampleNotFoundException();}).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_test_return_response() {

        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                request -> new RelifeResponse(200)).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
    }

    @Test
    void should_test_return_response_when_throw_exception_in_handler() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().addAction(
                "/path",
                RelifeMethod.GET,
                request ->  {throw new RuntimeException("error occurred");}).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(500, response.getStatus());
    }
}
