package com.test;

import com.smart.framework.annotation.*;
import org.apache.tools.ant.util.StringUtils;

/**
 * Created by Lishion on 2017/8/20.
 */
@Bean(value = BeanType.Controller,singleton = false)
public class Action {
    @Inject
    Service service;

    @Route("/test")
    @Before(Inter.class)
    public String aaa(@Var("x") String x){
        System.out.println("aaa");
        System.out.println(x==null?"null":x);
        System.out.println(service.toString());
        System.out.println(this.toString());
        
        return "lll";
    }

    @Route(value = "/test",method = RequestType.GET)
    @Before(Inter.class)
    public String bbb(@Var("x") String x){
        
        System.out.println("bbb");
        System.out.println(x==null?"null":x);
        System.out.println(service.toString());
        System.out.println(this.toString());

        return "lll";
    }

    @Override
    public String toString() {
        return "Action{" +
                "service=" + service.toString() +
                '}';
    }
}
