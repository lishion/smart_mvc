package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Var;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.exception.SetFieldException;
import com.smart.framework.layerm.ConverterFactory;
import com.smart.framework.layerm.StringConverter;
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

    @Override
    public Object resolve(ParamWrapper parameter, HttpServletRequest request, ConverterFactory factory) {
        if(parameter.getParameter().isAnnotationPresent(Var.class)){
            return resolveSimpleType(parameter,request,factory);
        }else if(BeanType.isModel(parameter.getParameter().getType())){
            return resolveModel(parameter,request,factory);
        }
        return null;
    }




    private Object resolveSimpleType(ParamWrapper parameter, HttpServletRequest request, ConverterFactory factory) {
        StringConverter stringConverter = (StringConverter) factory.get(parameter.getParameter().getType());
        if(stringConverter==null){
           return null;
        }
        return stringConverter.convert(request.getParameter(parameter.getParameter().getAnnotation(Var.class).value()));
    }


    private Object resolveModel(ParamWrapper parameter, HttpServletRequest request, ConverterFactory factory){

        Class<?> clazz = parameter.getClass();
        Field[] fields = clazz.getDeclaredFields();

        try {

            Object paramInstance = ReflectionKit.getObject(clazz);
            for (Field field : fields) {
                StringConverter stringConverter = (StringConverter) factory.get(field.getType());
                Object value = null;
                if (stringConverter != null) {
                    value = stringConverter.convert(request.getParameter(field.getName()));
                }
                ReflectionKit.setFiled(paramInstance, field, value);
            }
            return paramInstance;
            
        }
        catch (GetInstanceException|SetFieldException e) {
            e.printStackTrace();
            return null;
        }

    }
    

}
