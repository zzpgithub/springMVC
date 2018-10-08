package com.tw.relife.story4;

import com.tw.relife.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelifeMutiActionTest {

    @Test
    void should_test_add_multi_actions() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "get action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(403, "post action", "text/plain"))
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse responsePOST = app.process(new RelifeRequest("/path", RelifeMethod.POST));
        assertEquals(403, responsePOST.getStatus());
        assertEquals("post action", responsePOST.getContent());

        RelifeResponse responseGET = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, responseGET.getStatus());
        assertEquals("get action", responseGET.getContent());
    }

    @Test
    void should_test_two_request_with_same_method() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(200, "first action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(403, "second action", "text/plain"))
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse responsePOST = app.process(new RelifeRequest("/path", RelifeMethod.POST));
        assertEquals(200, responsePOST.getStatus());
        assertEquals("first action", responsePOST.getContent());
    }
}
