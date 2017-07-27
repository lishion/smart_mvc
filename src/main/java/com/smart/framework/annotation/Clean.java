package com.smart.framework.annotation;

import com.smart.framework.aop.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Lishion on 2017/7/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Clean {
    Class<? extends Interceptor> value()[] ;
}
