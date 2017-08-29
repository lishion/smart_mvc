package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.layerm.ConverterFactory;
import com.smart.framework.layerv.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Parameter;

/**
 * Created by Lishion on 2017/8/28.
 */
public class InternalTypeParamResolver implements ParamResolver {
    @Override
    public boolean can(Parameter parameter) {
        if(parameter.getType()==HttpServletRequest.class){
            return true;
        }else if(parameter.getType()== ModelAndView.class){
            return true;
        }
        return false;
    }

    @Override
    public Object resolve(ParamWrapper paramWrapper, RequestWrapper requestWrapper, ConverterFactory factory) {
        if(paramWrapper.getType()==HttpServletRequest.class){
            return requestWrapper.getRequest();
        }else if(paramWrapper.getType()==ModelAndView.class){
            return new ModelAndView();
        }
        return null;
    }
}
