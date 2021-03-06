package com.smart.framework.layerm;

import com.smart.framework.config.ConfigData;
import com.smart.framework.config.SmartConfig;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/9.
 * 转换器容器
 */
public class StringConverters implements ConverterFactory,ConfigData {
    private  Map<Class<?>,StringConverter> simpleConverters = new ConcurrentHashMap<>();
    private  Map<Class<?>,Object> defaultValue = new ConcurrentHashMap<>();
    public StringConverters() {
        init();
    }

    void init() {
        simpleConverters.put(BigDecimal.class,BigDecimal::new);
        simpleConverters.put(BigInteger.class,BigInteger::new);
        
        simpleConverters.put(Integer.class, Integer::parseInt);
        simpleConverters.put(Boolean.class, Boolean::parseBoolean);
        simpleConverters.put(Double.class, Double::parseDouble);
        simpleConverters.put(Float.class, Float::parseFloat);
        simpleConverters.put(Long.class, Long::parseLong);
        simpleConverters.put(String.class, s->s);//String 不做处理
        simpleConverters.put(Short.class, Short::parseShort);

        simpleConverters.put(int.class, Integer::parseInt);
        simpleConverters.put(boolean.class, Boolean::parseBoolean);
        simpleConverters.put(double.class, Double::parseDouble);
        simpleConverters.put(float.class, Float::parseFloat);
        simpleConverters.put(long.class, Long::parseLong);
        simpleConverters.put(short.class, Short::parseShort);
        simpleConverters.put(Map.class,new StringMapConverter());
        simpleConverters.put(Object[].class,new StringArrayConverter());


    }


    @Override
    public StringConverter get(Class<?> clazz){
        return simpleConverters.get(clazz);
    }

    public StringConverters add(Class<?> clazz , StringConverter converter){
        simpleConverters.put(clazz,converter);
        return this;
    }


    @Override
    public void fromConfig(SmartConfig smartConfig) {
        smartConfig.setDefaultValue(defaultValue);
    }
}
