package com.test;

import com.smart.framework.annotation.*;

/**
 * Created by Lishion on 2017/8/20.
 */
@Bean(value = BeanType.Controler , singleton = false)
@Before(Inter.class)
public class Action {
    @Inject
    Service service;

    @Route("/test")
    String test(){
        System.out.println(toString());
        return "lll";
    }

    @Override
    public String toString() {
        return "Action{" +
                "service=" + service +
                '}';
    }
}
