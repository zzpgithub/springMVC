package com.tw.relife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.relife.annotations.RelifeRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class RelifeMvcHandlerBuilder implements RelifeAppHandler{

    HashMap<String, HashMap<RelifeMethod, RelifeAppHandler>> requestActions = new HashMap<>();
    HashMap<String, HashMap<RelifeMethod, RelifeResponse>> actionsController = new HashMap<>();


    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler relifeAppHandler) {
        if (path == null || method == null || relifeAppHandler == null) {
            throw new IllegalArgumentException();
        }
        HashMap<RelifeMethod, RelifeAppHandler>  mh;

        if(requestActions.containsKey(path)){
            mh = requestActions.get(path);
            if(!mh.containsKey(method))
                mh.put(method, relifeAppHandler);
        }else{
            mh = new HashMap<>();
            mh.put(method, relifeAppHandler);
            requestActions.put(path, mh);
        }
        return this;
    }

    public RelifeAppHandler build(){
        return this;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) throws Exception {
        if(!requestActions.containsKey(request.getPath())){
            return new RelifeResponse(404);
        } else if (!requestActions.get(request.getPath()).containsKey(request.getMethod())) {
            return new RelifeResponse(404);
        }

        RelifeAppHandler relifeHandler = requestActions.get(request.getPath()).get(request.getMethod());

        return relifeHandler.process(request);
    }

    public RelifeMvcHandlerBuilder addController(Class<?> controllerClass) {

        checkInputFormat(controllerClass);

        Method[] methods = controllerClass.getDeclaredMethods();
        for(Method method : methods){
            Annotation[] annotations = method.getAnnotations();
            //Annotation declaredAnnotation = method.getDeclaredAnnotation(RelifeRequestMapping.class);///

            for(Annotation annotation : annotations){
                if(annotation.annotationType() == RelifeRequestMapping.class){

                    String path = ((RelifeRequestMapping)annotation).path();
                    RelifeMethod relifeMethod = ((RelifeRequestMapping)annotation).method();

                    RelifeAppHandler handler = request -> {
                        try {
                            method.setAccessible(true);
                            return (RelifeResponse) (method.invoke(controllerClass.newInstance(), request));
                        } catch (InvocationTargetException e) {
                            throw (Exception) e.getCause();
                        }
                    };
                    addAction(path, relifeMethod, handler);
//                    RelifeResponse returnResult = method.invoke(controllerClass.newInstance(), );
//                    HashMap<RelifeMethod, RelifeResponse>  mh = new HashMap<>();
//                    mh.put(relifeMethod, null );
//                    actionsController.put(path, mh);
                }
            }
        }
        return this;
    }



    private void checkInputFormat(Class controllerClass){
        if (controllerClass == null) {
            throw new IllegalArgumentException();
        }
    }
//    HashMap<String, HashMap<RelifeMethod, RelifeAppHandler>> actionsController = new HashMap<>();
//
//    private void add(String path, RelifeMethod method,  RelifeAppHandler relifeAppHandler){
//        HashMap<RelifeMethod, RelifeAppHandler>  mh;
//        if(actionsController.containsKey(path)){
//            mh = actionsController.get(path);
//            if(!mh.containsKey(method))
//                mh.put(method, relifeAppHandler);
//        }else{
//            mh = new HashMap<>();
//            mh.put(method, relifeAppHandler);
//            actionsController.put(path, mh);
//        }
//    }
}
