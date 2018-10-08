package com.tw.relife;

import com.tw.relife.annotations.RelifeStatusCode;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        // TODO: You can start here
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        // TODO: You can start here
        RelifeResponse response = null;
        try {
             response = handler.process(request);
             if(response == null){
                 return new RelifeResponse(200);
             }
             return response;
        } catch (Exception e) {
            RelifeStatusCode annotation = e.getClass().getAnnotation(RelifeStatusCode.class);
            if(annotation !=null){
                response = new RelifeResponse(annotation.value());
                return response;
            }else
            {
                response = new RelifeResponse(500);
                return response;
            }

        }
    }
}
