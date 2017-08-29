package com.smart.framework.core.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lishion on 2017/8/28.
 */
public interface RequestWrapperResolver {
    RequestWrapperResolver resolve(HttpServletRequest request) throws Exception;
    RequestWrapper build();

}
