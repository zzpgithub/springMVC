package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class SameActionController {
    @RelifeRequestMapping(path = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hello from",
                "text/plain");
    }

    @RelifeRequestMapping(path = "/path", method = RelifeMethod.GET)
    public RelifeResponse say(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hello from",
                "text/plain");
    }
}
