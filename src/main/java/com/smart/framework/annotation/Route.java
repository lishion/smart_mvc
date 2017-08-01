package com.smart.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Lishion on 2017/7/8.
 */
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface Route {
    String value()  ;
    String method() default RequestType.POST;
}
