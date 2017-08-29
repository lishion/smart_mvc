package com.smart.framework.layerc;

import com.smart.framework.annotation.FileVar;
import com.smart.framework.layerm.ConverterFactory;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/8/28.
 */
public class MultiPartResolver implements ParamResolver {
    @Override
    public boolean can(Parameter parameter) {
        return parameter.isAnnotationPresent(FileVar.class);
    }

    @Override
    public Object resolve(ParamWrapper paramWrapper, RequestWrapper requestWrapper, ConverterFactory factory) throws Exception {
        String name = paramWrapper.getParamAnnotation(FileVar.class).value();
        List<MultiPartFile> files = requestWrapper.getFileItems();

        if(files==null||files.size()<=0) {
            return new Exception("no file named: " + name);
        }
        if(paramWrapper.getType() == MultiPartFile.class){//如果是一个文件
            if(files.size()>1){
                throw new  Exception("more than one files named: " + name);
            }
            return files.get(0);
        }
        else if(paramWrapper.getType().isArray()){//如果是数组
            return requestWrapper.getFileArray(name);
        }else if(List.class.isAssignableFrom(paramWrapper.getType())){
            return files;
        }
        return null;
    }
}
