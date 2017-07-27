package com.smart.framework.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Lishion on 2017/7/7.
 * 通过文件名，包名加载class
 */
public class ClassUtil {

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }
    final static char separator = File.separatorChar; //文件分割符
    final static String classPath = getClassPath();

    public static Class<?> loadClass(String name,boolean isInitialized) throws ClassNotFoundException {
        Class<?> clazz;
        clazz = Class.forName(name,isInitialized,getClassLoader());
        return clazz;
    }

    public static String getClassPath(){
        Path classPathObj = null;
        try {
            classPathObj = Paths.get( getClassLoader().getResource("").toURI() );
        }catch (Exception e){
            System.err.println("can't get class path");
            e.printStackTrace();

        }
        return classPathObj.toString();
    }

    public static Set<Class<?>> getClassAt(List<String> topPackageNames) throws Exception {

        Set<Class<?>> classes = new HashSet<>();
        UrlUtil urlUtil = new UrlUtil();
        urlUtil.getAllClassName(topPackageNames).forEach(s->{
            try {
                classes.add(loadClass(s.trim(),false));
            }catch (ClassNotFoundException e){
                System.out.println("can't get instance for class:" + s.trim());
                e.printStackTrace();
            }
        });
        return  classes;
    }

    public static void visitField(Class<?> clazz , Consumer<Field> consumer){
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            consumer.accept(field);
        }
    }

    public static void visitMethod(Class<?> clazz , Consumer<Method> consumer){
        Method[] methods = clazz.getMethods();
        for(Method method:methods){
            consumer.accept(method);
        }
    }

}
