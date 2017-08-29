package com.smart.framework.core.param;

import com.smart.framework.core.param.ParamResolver;
import com.smart.framework.core.param.ParamWrapper;
import com.smart.framework.core.request.RequestWrapper;
import com.smart.framework.layerm.ConverterFactory;
import com.smart.framework.layerv.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
