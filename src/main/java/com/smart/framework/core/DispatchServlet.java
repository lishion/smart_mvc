package com.smart.framework.core;

import com.smart.framework.config.ServerType;
import com.smart.framework.config.Theme;
import com.smart.framework.core.param.*;
import com.smart.framework.core.request.*;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/8.
 */
/**
 * Created by Lishion on 2017/7/8.
 */
@WebServlet(value = "/",name = "dispatcher")
public class DispatchServlet extends HttpServlet {
    FrameContext frameContext = FrameContext.getInstance();
    private static ParamResolverFactory resolverFactory = new ParamResolverFactory();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encoding = frameContext.getTheme().getEncoding();
        if(encoding==null){
            req.setCharacterEncoding("utf-8");
        }else {
            req.setCharacterEncoding(encoding);
        }

        String context = req.getHeader("Content-Type");
        AnnotationHandlerMatcher matcher = new AnnotationHandlerMatcher();
        RequestWrapperResolver wrapperResolver = new RequestWrapperResolverFactory().get(context);

        try {

            RequestWrapper requestWrapper = wrapperResolver.resolve(req).build();
            String path = requestWrapper.getRequestPath();
            String method = requestWrapper.getRequestMethod();
            RequestHandler handler = matcher.get(path,method,frameContext.getRequestHandlers());
            Parameter[] parameters = handler.getHandlerMethod().getParameters();
            List<Object> objects =  new ArrayList<>();
            for (Parameter parameter : parameters) {
                ParamResolver resolver = resolverFactory.get(parameter);
                if( resolver == null ){
                    objects.add(null);
                }
                else{

                    ParamWrapper paramWrapper = new ParamWrapper();
                    paramWrapper.setParameter(parameter);
                    paramWrapper.setMethod(handler.getHandlerMethod());
                    objects.add(resolver.resolve(paramWrapper,requestWrapper,frameContext.getConverters()));

                }
            }
            Object o = frameContext.getBeanFactory().get( handler.getHandlerMethod().getDeclaringClass() );
            Object result =  handler.handle(o,objects.toArray());
            Dispatcher dispatcher = new Dispatcher(req,resp);
            dispatcher.dispatch(result);

        }catch (NoSuchHandlerException | MultiHandlerException | GetBeanException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void init(ServletConfig config) throws ServletException {







        resolverFactory.set(new FormParamResolver());
        resolverFactory.set(new RestfulParamResolver());
        resolverFactory.set(new InternalTypeParamResolver());
        resolverFactory.set(new MultipartParamResolver());
        resolverFactory.set(new JsonTypeParamResolver());


    }
}
