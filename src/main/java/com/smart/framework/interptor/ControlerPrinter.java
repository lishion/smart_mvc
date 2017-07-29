package com.smart.framework.interptor;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Invocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by Lishion on 2017/7/22.
 */
public class ControlerPrinter implements Interceptor {
    @Override
    public void intercept(Invocation iv) throws Throwable {

        Method method = iv.getMethod();
        System.out.println("| - - - - - - - - - smart-mvc start - - - - - - - - - -");
        System.out.println("| controler:" + iv.getClazz().getName());
        System.out.print("| args:");
        Object[] parameters = iv.getArgs();

        if(parameters.length<1){
            System.out.println("|no args");
        }else{
            for(Object parameter:parameters){
                System.out.print(parameter.getClass().getSimpleName());
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println();

        iv.invoke();
        System.out.println();


        System.out.println("| return:" + iv.getResult().getClass().getSimpleName());
        System.out.println("| - - - - - - - - - smart-mvc start - - - - - - - - - -");

    }
}
