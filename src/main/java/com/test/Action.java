package com.test;

import com.smart.framework.annotation.*;
import com.smart.framework.layerv.ModelAndView;
import org.apache.tools.ant.util.StringUtils;

/**
 * Created by Lishion on 2017/8/20.
 */
@Bean(value = BeanType.Controller,singleton = false)
public class Action {
    @Inject
    Service service;

    @Route("/index")
    @Before(Inter.class)
    public ModelAndView aaa(){
        ModelAndView modelAndView = new ModelAndView("page/index.jsp");
        return modelAndView;
    }

    @Route(value = "/test",method = RequestType.DEFAULT)
    @Before(Inter.class)
    public User bbb(@Var("x") String x,@Var("user") User user){

        System.out.println(x==null?"null":x);
        System.out.println(user==null?"null":user);
        return user;
    }

    @Override
    public String toString() {
        return "Action{" +
                "service=" + service.toString() +
                '}';
    }
}
