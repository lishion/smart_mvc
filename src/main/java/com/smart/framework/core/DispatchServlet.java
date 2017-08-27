package com.smart.framework.core;

import com.smart.framework.config.Theme;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;
import com.smart.framework.layerc.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lishion on 2017/7/8.
 */
@WebServlet("/")
public class DispatchServlet extends HttpServlet {
    private static FrameContext frameContext = new FrameContext();
    private static ParamResolverFactory resolverFactory = new ParamResolverFactory();

    private String getRequestUrl(HttpServletRequest request){

        String contextPath = request.getContextPath();//得到项目路径
        String requestUrl =  request.getRequestURL().toString();
        return requestUrl.substring(requestUrl.indexOf(contextPath)+contextPath.length(),requestUrl.length());
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = getRequestUrl(req);
        String method = req.getMethod().toUpperCase();

        AnnotationHandlerMatcher matcher = new AnnotationHandlerMatcher();
        try {
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
                    paramWrapper.setMethodClazz(handler.getHandlerMethod().getClass());
                    paramWrapper.setRequestUrl(path);
                    paramWrapper.setParameter(parameter);
                    paramWrapper.setRequestMethod(method);
                    paramWrapper.setMethod(handler.getHandlerMethod());
                    objects.add(resolver.resolve(paramWrapper,req,frameContext.getConverters()));

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

        super.init(config);
        ServletRegistration registration = getServletContext().getServletRegistration("default");
        registration.addMapping("*.js");
        registration.addMapping("*.html");
        registration.addMapping("*.css");
        Theme theme = frameContext.getTheme();
        theme.getAssets().forEach(s -> registration.addMapping("*"+s));

        ServletRegistration registration1 = getServletContext().getServletRegistration("jsp");
        registration1.addMapping("*.jsp");

        resolverFactory.set(new FormParamResolver());
        resolverFactory.set(new RestfulParamResolver());

    }
}
