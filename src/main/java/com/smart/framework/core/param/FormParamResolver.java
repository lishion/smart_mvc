package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Var;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.exception.SetFieldException;
import com.smart.framework.layerm.ConverterFactory;
import com.smart.framework.layerm.StringConverter;
import com.smart.framework.utils.Constants;
import com.smart.framework.utils.ReflectionKit;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * 用于支持有@var注解或用户定义的有@Model注解的实体类为类型的参数
 */
public class FormParamResolver implements ParamResolver {


    @Override
    public boolean can(Parameter parameter) {
        if(parameter.isAnnotationPresent(Var.class)){
            return true;
        }else if(BeanType.isModel(parameter.getType())){
            return true;
        }
        return false;
    }

    private boolean isModel(ParamWrapper paramWrapper){
        return  BeanType.isModel(paramWrapper.getParameter().getType());
    }
    private boolean isVar(ParamWrapper paramWrapper){
        return paramWrapper.getParamAnnotation(Var.class)!=null;
    }

    @Override
    public Object resolve(ParamWrapper parameter, RequestWrapper requestWrapper, ConverterFactory factory) {
        if(isVar(parameter)&&!isModel(parameter)){
            return resolveSimpleType(parameter,requestWrapper,factory);
        }else if(isModel(parameter)){
            return resolveModel(parameter,requestWrapper,factory);
        }
        return null;
    }



    private Object resolveSimpleType(ParamWrapper parameter, RequestWrapper requestWrapper, ConverterFactory factory) {
        StringConverter stringConverter = (StringConverter) factory.get(parameter.getType());
        if(stringConverter==null){
           return null;
        }
        return stringConverter.convert(requestWrapper.getParam(parameter.getParamAnnotation(Var.class).value()));
    }


    private Object resolveModel(ParamWrapper parameter, RequestWrapper requestWrapper, ConverterFactory factory){

        Class<?> clazz = parameter.getType();
        Field[] fields = clazz.getDeclaredFields();
        String prefix = isVar(parameter) ? parameter.getParamAnnotation(Var.class).value()+".": Constants.EMPTY_STR;

        try {
            Object paramInstance = ReflectionKit.getObject(clazz);
            for (Field field : fields) {
                StringConverter stringConverter = (StringConverter) factory.get(field.getType());
                Object value = null;
                if (stringConverter != null) {
                    value = stringConverter.convert(requestWrapper.getParam(prefix+field.getName()));
                }
                ReflectionKit.setFiled(paramInstance, field, value);
            }
            return paramInstance;
            
        }
        catch (GetInstanceException|SetFieldException e) {
            System.err.println("bind parameter error!");
            e.printStackTrace();
            return null;
        }

    }
    

}
