package com.tw.relife;

import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RelifeMvcHandlerBuilder implements RelifeAppHandler{

    HashMap<String, HashMap<RelifeMethod, RelifeAppHandler>> requestActions = new HashMap<>();
    private Set<Class<?>> controllers = new HashSet<>();


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
                }
            }
        }
        controllers.add(controllerClass);
        return this;
    }



    private void checkInputFormat(Class<?> controllerClass){
        if (controllerClass == null) {
            throw new IllegalArgumentException();
        }
        if (controllers.contains(controllerClass)) {
            throw new IllegalArgumentException();
        }
        if (Modifier.isInterface(controllerClass.getModifiers())
                || Modifier.isAbstract(controllerClass.getModifiers())) {
            throw new IllegalArgumentException();
        }
        if (!controllerClass.isAnnotationPresent(RelifeController.class)) {
            throw new IllegalArgumentException();
        }
        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation declaredAnnotation = method.getDeclaredAnnotation(RelifeRequestMapping.class);
            if (declaredAnnotation != null && (method.getParameterCount() != 1 || method.getParameterTypes()[0] != RelifeRequest.class)) {
                throw new IllegalArgumentException();
            }
        }
    }
}
