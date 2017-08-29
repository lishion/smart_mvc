package com.smart.framework.core.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by Lishion on 2017/8/28.
 */
public class FormRequestWrapper extends DefaultRequestWrapperResolver {
    @Override
    public RequestWrapperResolver resolve(HttpServletRequest request) {
        super.resolve(request);
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            paramMap.put(key, request.getParameter(key));
        }
        return this;
    }
}
