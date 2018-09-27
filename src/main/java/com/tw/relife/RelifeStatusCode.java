package com.tw.relife;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RelifeStatusCode {
    int value();
}
