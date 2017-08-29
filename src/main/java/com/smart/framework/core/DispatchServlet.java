package com.smart.framework.core;

import com.smart.framework.config.ServerType;
import com.smart.framework.config.Theme;
import com.smart.framework.core.param.*;
import com.smart.framework.core.request.*;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;


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
@WebServlet("/")
public class DispatchServlet extends HttpServlet {
    static FrameContext frameContext = null;
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

        try {
            RequestWrapperResolver wrapperResolver = new RequestWrapperResolverFactory().get(context);
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

        super.init(config);

        if(frameContext == null){
            frameContext = new FrameContext();
            frameContext.run();
        }

       if(frameContext.getTheme().getServerType()== ServerType.TOMCAT){

           ServletRegistration registration = getServletContext().getServletRegistration("default");
           ServletRegistration registration1 = getServletContext().getServletRegistration("jsp");
           ServletContext context = getServletContext();

           registration.addMapping("*.js");
           registration.addMapping("*.html");
           registration.addMapping("*.css");
           registration1.addMapping("*.jsp");
           registration1.addMapping("*.ico");

           Theme theme = frameContext.getTheme();
           theme.getAssets().forEach(s -> registration.addMapping("*"+s));

       }

        resolverFactory.set(new FormParamResolver());
        resolverFactory.set(new RestfulParamResolver());
        resolverFactory.set(new InternalTypeParamResolver());
        resolverFactory.set(new MultipartParamResolver());


    }
}
