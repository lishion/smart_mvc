package com.smart.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Lishion on 2017/7/21.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    BeanType value() default BeanType.Component;
}
