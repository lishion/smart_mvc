package com.test;

import com.smart.framework.annotation.*;

/**
 * Created by Lishion on 2017/8/20.
 */
@Bean(value = BeanType.Controller , singleton = true)
@Before(Inter.class)
public class Action {
    @Inject
    Service service;

    @Route("/test")
    String aaa(){
        System.out.println(toString());
        return "lll";
    }

    @Override
    public String toString() {
        return "Action{" +
                "service=" + service +
                '}'+this.hashCode();
    }
}
