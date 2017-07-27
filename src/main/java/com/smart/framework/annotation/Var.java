package com.smart.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Lishion on 2017/7/15.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Var {
    String value();
}
