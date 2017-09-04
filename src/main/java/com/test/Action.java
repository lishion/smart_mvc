package com.test;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;
import com.smart.framework.annotation.*;
import com.smart.framework.core.request.MultipartFile;
import com.smart.framework.layerv.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lishion on 2017/8/20.
 */

@Bean(value = BeanType.Controller,singleton = false)
public class Action {
    @Inject
    Service service;

    @Route(value = "/index",method = RequestType.GET)
    public ModelAndView aaa(ModelAndView mv){
        mv.putData("key","hello world");
        return mv.setView("page/index.jsp");
    }

    @Route(value = "/test")
    @Before(Inter.class)
    public User test(User s){

        return s;
    }

    @Route(value = "/file")
    public void file(@FileVar("file") MultipartFile files){
        File file =new File("hello world.text");
        try {
            files.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Route(value = "/user")
    public User file(@JsonVar User user){

        return user;
    }


}
