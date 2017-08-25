package com.smart.framework.layerc;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParamResolverFactory {
    private List<ParamResolver> paramResolvers = new ArrayList<>(5);
    public ParamResolver get(Parameter parameter){
        for (ParamResolver paramResolver : paramResolvers) {
            if(paramResolver.can(parameter)){
                return paramResolver;
            }
        }
        return null;
    }
    public void set(ParamResolver resolver){
        paramResolvers.add(resolver);
    }

}
