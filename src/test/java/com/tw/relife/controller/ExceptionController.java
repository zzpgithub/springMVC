package com.tw.relife.controller;

import com.tw.relife.ControllerThrowException;
import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class ExceptionController {
    @RelifeRequestMapping(path = "/path", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request){
        throw new ControllerThrowException();
    }

    @RelifeRequestMapping(path = "/null", method = RelifeMethod.GET)
    public RelifeResponse testNull(RelifeRequest request) {
        return null;
    }

    @RelifeRequestMapping(path = "/run_time", method = RelifeMethod.GET)
    public RelifeResponse testRuntime(RelifeRequest request) {
        throw new RuntimeException();
    }

}
