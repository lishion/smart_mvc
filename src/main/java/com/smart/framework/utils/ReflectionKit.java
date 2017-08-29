package com.smart.framework.utils;

import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.exception.InvokeMethodException;
import com.smart.framework.exception.SetFieldException;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Lishion on 2017/7/8.
 */
public class ReflectionKit {

    /**
     *
     * @param clazz:类的class属性
     * @return 对应的类的对象
     * @throws Exception
     */
    public static Object getObject(Class<?> clazz) throws GetInstanceException {

        if(clazz == null){
            return null;
        }
        if( !canGetInstance(clazz) ){
            return null;
        }
        try {
            return clazz.newInstance();
        }catch ( InstantiationException|IllegalAccessException e ){
            e.printStackTrace();
            throw new GetInstanceException("get instance for class:"+clazz.getName()+"error!");
        }

    }
    public static boolean canGetInstance(Class<?> clazz){
        return !(Modifier.isAbstract( clazz.getModifiers() ) || Modifier.isInterface( clazz.getModifiers()));
    }

    /**
     * 调用目标对象的目标方法
     * @param object:目标对象
     * @param method:目标方法
     * @param args:可变参数(数组)
     * @return 该方法的返回值
     */
    public static Object invokeMethod(Object object, Method method,Object ...args) throws InvokeMethodException {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object,args);
            return result;
        }catch (IllegalAccessException|InvocationTargetException e){
            e.printStackTrace();
          throw new InvokeMethodException("invoke method: " + method.getName() + "() error!");
        }
    }

    /**
     * 设置目标对象的目标属性
     * @param object:目标对象
     * @param field:目标属性
     * @param value:需要设置的值
     */
    public static void setFiled(Object object, Field field, Object value) throws SetFieldException {
        try {
            field.setAccessible(true);
            field.set(object,value);
        }catch (IllegalAccessException e){
            e.printStackTrace();
            throw new SetFieldException("set filed:"+field.getName()+"in class:"+object.getClass().getName()+"error!!");
        }

    }
}
