package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class ControllerTwoParam {
    @RelifeRequestMapping(path = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request, RelifeRequest request2) {
        return new RelifeResponse(
                200,
                "Hello from " + request.getPath(),
                "text/plain");
    }
}
