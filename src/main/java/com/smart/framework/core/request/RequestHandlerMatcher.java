package com.smart.framework.core.request;

import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;

import java.util.List;

public interface RequestHandlerMatcher {
    RequestHandler get(String requestUrl, String requestMethod,List<RequestHandler> handlers) throws NoSuchHandlerException, MultiHandlerException;
}
