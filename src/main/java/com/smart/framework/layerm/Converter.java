package com.smart.framework.layerm;

/**
 * Created by Lishion on 2017/7/9.
 */

@FunctionalInterface
public interface Converter<fromType ,toType> {
     toType convert(Class<?> clazz,fromType t) throws Exception;
}
