package com.smart.framework.layerm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Lishion on 2017/7/9.
 * 转换器容器
 */
public class ConverterContainer {
    private  Map<Class<?>,StringConverter> simpleConvertMap = new ConcurrentHashMap<>();
    private  Map<Class<?>,Converter> userConverterMap = new ConcurrentHashMap<>();

    void init() {

        simpleConvertMap.put(Integer.class, (clazz, s) -> Integer.parseInt(s));
        simpleConvertMap.put(Boolean.class, (clazz, s) -> Boolean.parseBoolean(s));
        simpleConvertMap.put(Double.class, (clazz, s) -> Double.parseDouble(s));
        simpleConvertMap.put(Float.class, (clazz, s) -> Float.parseFloat(s));
        simpleConvertMap.put(Long.class, (clazz, s) -> Long.parseLong(s));
        simpleConvertMap.put(String.class, (clazz, s) -> s);//String 不做处理
        simpleConvertMap.put(Short.class, (clazz, s) -> Short.parseShort(s));

        simpleConvertMap.put(int.class, (clazz, s) -> Integer.parseInt(s));
        simpleConvertMap.put(boolean.class, (clazz, s) -> Boolean.parseBoolean(s));
        simpleConvertMap.put(double.class, (clazz, s) -> Double.parseDouble(s));
        simpleConvertMap.put(float.class, (clazz, s) -> Float.parseFloat(s));
        simpleConvertMap.put(long.class, (clazz, s) -> Long.parseLong(s));
        simpleConvertMap.put(short.class, (clazz, s) -> Short.parseShort(s));

    }


    public   StringConverter getSimpleConverter(Class<?> clazz){
        return simpleConvertMap.get(clazz);
    }

    public  Converter getUserconverter(Class<?> clazz){
        return  userConverterMap.get(clazz);
    }
    
    public  void putUserConvert(Class<?> clazz , Converter converter){
        userConverterMap.put(clazz,converter);
    }

}
