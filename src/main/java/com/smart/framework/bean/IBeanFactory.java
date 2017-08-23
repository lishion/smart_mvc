package com.smart.framework.bean;

import com.smart.framework.exception.GetBeanException;

public interface IBeanFactory {
    <T> T  get(Class<T> clazz) throws GetBeanException;//通过clazz得到bean
}
