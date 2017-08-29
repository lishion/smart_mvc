package com.smart.framework.layerc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lishion on 2017/8/28.
 */
public interface RequestWrapperResolver {
    RequestWrapperResolver resolve(HttpServletRequest request);
    RequestWrapper build();

}
