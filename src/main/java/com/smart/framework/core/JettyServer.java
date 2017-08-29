package com.smart.framework.core;

/*import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;*/

/**
 * Created by Lishion on 2017/8/27.
 */
public class JettyServer {
    private String contextPath;
    private String resourceBase;
    private int port =  8080;

    public JettyServer(String contextPath, String resourceBase, int port) {
        this.contextPath = contextPath;
        this.resourceBase = resourceBase;
        this.port = port;
    }

    public void run() throws Exception {

      /*  Server server = new Server(port);
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(DefaultServlet.class,"*.css");
        servletHandler.addServletWithMapping(DefaultServlet.class,"*.js");
        servletHandler.addServletWithMapping(DefaultServlet.class,"*.html");
        servletHandler.addServletWithMapping(DefaultServlet.class,"*.ico");

        loadContext().getTheme().getAssets()//添加静态资源
                .forEach(s->servletHandler.addServletWithMapping(DefaultServlet.class,"*"+s));

        servletHandler.addServletWithMapping(DispatchServlet.class,"/");
        Configuration.ClassList classList = Configuration.ClassList
                .setServerDefault( server );
        classList.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration" );

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.insertHandler(servletHandler);
        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*//**//*[^/]*servlet-api-[^/]*\\.jar$|.*//**//*javax.servlet.jsp.jstl-.*\\.jar$|.*//**//*[^/]*taglibs.*\\.jar$" );
        server.insertHandler(webAppContext);
        server.start();
        server.join();*/
    }

    private FrameContext loadContext(){
        FrameContext frameContext = new FrameContext();
        frameContext.run();
        DispatchServlet.frameContext = frameContext;
        return frameContext;
    }

}
