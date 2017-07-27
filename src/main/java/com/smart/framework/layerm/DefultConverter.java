package com.smart.framework.layerm;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.utils.ClassUtil;
import com.smart.framework.utils.ReflectionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lishion on 2017/7/9.
 * 默认转换器
 */
public class DefultConverter implements ModelConverter {

    @Override
    public Object convert(Class<?> clazz, HttpServletRequest t) throws Exception {

        if(BeanType.isModel(clazz)){
            throw new Exception("can't bind data to type:"+clazz.getTypeName()+" without Model annotaion!");
        }
        Object o = null;
        try {
               o = ReflectionUtil.getObject(clazz);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("bind data to Object with class"+ clazz.getName()+"error!!");
            }
        Object oo = o;

        ClassUtil.visitField(clazz, field -> {
            Converter converter = ConverterContainer.getUserconverter( field.getType() );//首先寻找用户自定义的转换器
            if( BeanType.isModel(clazz) ){//如果定义的变量中有Model注解的变量 则需要递归赋值
                   converter = converter == null ? this : converter;//如果没有找到用户自定义的转换器 则使用默认转换器
               try {
                   ReflectionUtil.setFiled( oo ,field,converter.convert( field.getType(),t ) );
               }
               catch (Exception e){
                   System.err.println("set filed error when bind data!!!");
                   e.printStackTrace();
               }
            }
            else{

                converter = converter == null ? ConverterContainer.getSimpleConverter( field.getType() ) : converter;//如果没有找到用户自定义的转换器 则使用简单转换器
                String s = t.getParameter(field.getName());//从request中获取值
                if( s==null ){
                    s = field.getType() == String.class ? "null" : "0";//避免空指针
                }
                try {
                    ReflectionUtil.setFiled( oo ,field,converter.convert( field.getType(),t ) );
                }
                catch (Exception e){
                    System.err.println("set filed error when bind data!!!");
                    e.printStackTrace();
                }

            }
        });
        return o;
    }
}
