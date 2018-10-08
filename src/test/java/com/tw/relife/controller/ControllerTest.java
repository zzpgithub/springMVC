package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class ControllerTest {
    @RelifeRequestMapping(path = "/test", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request) {
        return new RelifeResponse(201, "test", "text/plain");
    }
}
