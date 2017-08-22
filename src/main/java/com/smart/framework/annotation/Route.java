package com.smart.framework.annotation;

import com.smart.framework.utils.Constants;

import java.lang.annotation.*;

/**
 * Created by Lishion on 2017/7/8.
 */
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface Route {
    String value()  ;
    String method() default Constants.EMPTY_STR;
}
