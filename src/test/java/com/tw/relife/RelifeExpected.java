package com.tw.relife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelifeExpected{

    @Test
    void should_create_response(){
        RelifeApp app=new RelifeApp(request->{
            throw new SampleNotFoundException();
        });

        RelifeRequest whateverRequest=new RelifeRequest("/any/path",RelifeMethod.GET);
        RelifeResponse response=app.process(whateverRequest);

        assertEquals(404, response.getStatus());
    }
}
