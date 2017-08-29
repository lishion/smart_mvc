package com.test;

import com.smart.framework.core.DispatchServlet;
import com.smart.framework.core.JettyServer;
/*import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.JspPropertyGroupServlet;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;*/
/*import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;*/



/**
 * Created by Lishion on 2017/8/27.
 */
public class ServerEntry {
    public static void main(String args[]) throws Exception {
        JettyServer jettyServer = new JettyServer("/","web",80);
        jettyServer.run();
    }
}
