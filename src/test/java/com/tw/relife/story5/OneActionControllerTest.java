package com.tw.relife.story5;

import com.tw.relife.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
