package com.smart.framework.core.param;

import com.smart.framework.core.request.RequestWrapper;
import com.smart.framework.layerm.ConverterFactory;

import java.lang.reflect.Parameter;

public interface ParamResolver {
    boolean can(Parameter parameter);
    Object resolve(ParamWrapper paramWrapper, RequestWrapper requestWrapper , ConverterFactory factory) throws Exception;
}
