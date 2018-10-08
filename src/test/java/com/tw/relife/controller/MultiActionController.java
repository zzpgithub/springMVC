package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class MultiActionController {
    @RelifeRequestMapping(path = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hello from",
                "text/plain");
    }

    @RelifeRequestMapping(path = "/path_two", method = RelifeMethod.GET)
    public RelifeResponse say(RelifeRequest request) {
        return new RelifeResponse(
                403,
                "Hello from",
                "text/plain");
    }
}
