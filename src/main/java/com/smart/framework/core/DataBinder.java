package com.smart.framework.core;

import com.smart.framework.annotation.Var;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lishion on 2017/7/15.
 */
public class DataBinder {
    Parameter[] parameters  = null;
    HttpServletRequest req = null;

    public DataBinder(Parameter[] parameters, HttpServletRequest req){

      
        this.parameters = parameters;
        this.req = req;

    }

    /**
     * 将从request中获取的值绑定到参数
     * @return 返回绑定后的参数
     * @throws Exception
     */
    public Object[] bind() throws Exception {

        //ParamObjectConverter paramObjectConverter = new ParamObjectConverter();

        List<Object> objects = new ArrayList<>();
        for (Parameter parameter : parameters) {

            Class<?> clazz = parameter.getType();
            Object o = null;
            if (parameter.isAnnotationPresent(Var.class)) {
                 o = requestVarBinder(parameter,clazz);
            } else if(true){//todo:绑定框架需要的变量 如HttpServletRequest ModelAndView

            }
            else{
                // o = paramObjectConverter.convert(parameter.getType(), req);
            }
            objects.add(o);
        }
        return objects.toArray();
    }

    /**
     * 绑定简单数据类型
     * @param parameter 需要绑定的简单数据参数对象
     * @param clazz 参数类class
     * @return 转换后的对象
     * @throws Exception
     */
    private Object requestVarBinder(Parameter parameter, Class<?> clazz) throws Exception{
         return null;
    }
}
