package com.smart.framework.control;

import com.smart.framework.annotation.*;
import com.smart.framework.interptor.ControlerPrinter;
import com.smart.framework.interptor.GCInterptor;
import com.smart.framework.layerv.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/10.
 */


@Bean(BeanType.Controler)//注解为控制器
@Route("/test/")
public class TestAction {

    @Inject(TestIoc.class)//需要自动注入
    private TestIoc testIoc = null;

    @Before(GCInterptor.class)//拦截器
    @Route(value = "hello",method = RequestType.GET)//路由
    public ModelAndView test1(){
        System.out.println(testIoc);
        ModelAndView mv = new ModelAndView("/page/index.jsp");
        mv.putData("END","LISHION");
        mv.putData("Title","haha");
        System.out.println("hello");
        return mv;//返回视图和数据
    }

    @Route("world")
    public Map<String ,Object> test(@Var("x") String s, HttpServletRequest request){

        System.out.println(request.getParameter("x"));
        Map<String,Object> map = new HashMap<>();
        map.put("x",1);
        map.put("y","2");
        map.put("z","hello world");
                map.put("s",s);

                return map;//只返回数据
                }

                }
