package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;
import com.tw.relife.entity.Person;

@RelifeController
public class ControllerJson {
    @RelifeRequestMapping(path = "/json", method = RelifeMethod.GET)
    public Person testPerson(RelifeRequest request) {
        return new Person("Jack", 1);
    }
}
