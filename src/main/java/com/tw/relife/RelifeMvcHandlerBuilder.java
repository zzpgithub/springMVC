package com.tw.relife;

import java.util.HashMap;

public class RelifeMvcHandlerBuilder implements RelifeAppHandler{

    HashMap<String, HashMap<RelifeMethod, RelifeAppHandler>> requestActions = new HashMap<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler relifeAppHandler) {
        if (path == null || method == null || relifeAppHandler == null) {
            throw new IllegalArgumentException();
        }
        HashMap<RelifeMethod, RelifeAppHandler>  mh= new HashMap<>();
        mh.put(method, relifeAppHandler);
        requestActions.put(path, mh);
        return this;
    }

    public RelifeAppHandler build(){
        return this;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        if(!requestActions.containsKey(request.getPath())){
            return new RelifeResponse(404);
        } else if (!requestActions.get(request.getPath()).containsKey(request.getMethod())) {
            return new RelifeResponse(404);
        }

        RelifeAppHandler relifeHandler = requestActions.get(request.getPath()).get(request.getMethod());

        return relifeHandler.process(request);
    }
}
