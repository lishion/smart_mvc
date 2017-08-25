package com.smart.framework.layerc;

import com.smart.framework.layerm.ConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public interface ParamResolver {
    boolean can(Parameter parameter);
    Object resolve(ParamWrapper paramWrapper, HttpServletRequest request , ConverterFactory factory);
}
