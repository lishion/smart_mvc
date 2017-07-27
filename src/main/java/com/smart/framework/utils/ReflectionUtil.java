package com.smart.framework.utils;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Lishion on 2017/7/8.
 */
public class ReflectionUtil {

    /**
     *
     * @param clazz:类的class属性
     * @return 对应的类的对象
     * @throws Exception
     */
    public static Object getObject(Class<?> clazz) throws Exception {

        if(clazz == null){
            return null;
        }
        if( Modifier.isAbstract( clazz.getModifiers() ) || Modifier.isInterface( clazz.getModifiers() ) ){
            return null;
        }
        try {
            return clazz.newInstance();
        }catch ( InstantiationException|IllegalAccessException e ){
            e.printStackTrace();
            throw new Exception("get instance for class:"+clazz.getName()+"error!");
        }

    }

    /**
     * 调用目标对象的目标方法
     * @param object:目标对象
     * @param method:目标方法
     * @param args:可变参数(数组)
     * @return 该方法的返回值
     */
    public static Object invokeMethod(Object object, Method method,Object ...args) throws Exception {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object,args);
            return result;
        }catch (IllegalAccessException|InvocationTargetException e){
            e.printStackTrace();
          throw new Exception("invoke method:" + method.getName() + " error!");
        }
    }

    /**
     * 设置目标对象的目标属性
     * @param object:目标对象
     * @param field:目标属性
     * @param value:需要设置的值
     */
    public static void setFiled(Object object, Field field, Object value) throws Exception {
        try {
            field.setAccessible(true);
            field.set(object,value);
        }catch (IllegalAccessException e){
            e.printStackTrace();
            throw new Exception("set filed:"+field.getName()+"in class:"+object.getClass().getName()+"error!!");
        }
    }
}
