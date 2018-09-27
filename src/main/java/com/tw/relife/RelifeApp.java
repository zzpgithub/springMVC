package com.tw.relife;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        // TODO: You can start here
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        //throw new NotImplementedException();
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        // TODO: You can start here
        RelifeResponse response = null;
        try {
             response = handler.process(request);
             return response;
//            handler.process(request);
        } catch (Exception e) {
            response = new RelifeResponse(500);
            return response;
        }

//        return new RelifeResponse(handler.process(request).getStatus());
        //throw new NotImplementedException();
    }
}
