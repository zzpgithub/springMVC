package com.tw.relife.annotations;

import com.tw.relife.RelifeMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RelifeRequestMapping {
    String path();
    RelifeMethod method();
}
