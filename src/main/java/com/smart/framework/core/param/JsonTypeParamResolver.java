package com.smart.framework.core.param;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.framework.annotation.JsonVar;
import com.smart.framework.core.request.RequestWrapper;
import com.smart.framework.layerm.ConverterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;

/**
 * Created by Lishion on 2017/8/30.
 */
public class JsonTypeParamResolver implements ParamResolver{

    @Override
    public boolean can(Parameter parameter) {

        return parameter.isAnnotationPresent(JsonVar.class);
    }

    @Override
    public Object resolve(ParamWrapper paramWrapper, RequestWrapper requestWrapper, ConverterFactory factory) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = requestWrapper.getRequest().getInputStream();
            Class<?> clazz = paramWrapper.getType();
            return objectMapper.readValue(is,clazz);
        }catch (IOException e){
            throw new Exception("get inputStream error when resolve json type parameter!");
        }

    }
}
