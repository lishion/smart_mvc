package com.smart.framework.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.framework.layerv.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lishion on 2017/7/15.
 */
public class Dispatcher {
    private HttpServletRequest request;
    private HttpServletResponse resopnse;

    public Dispatcher(HttpServletRequest request, HttpServletResponse resopnse) {

        this.request = request;
        this.resopnse = resopnse;

    }

    /**
     *
     * @param result 根据控制器返回的内容进行请求转发
     * @throws ServletException
     * @throws IOException
     */
    public void dispatch(Object result) throws Exception {

       if(result instanceof ModelAndView){
           ModelAndView mv = (ModelAndView)result;
           (mv).visitData((k,v)->{
               request.setAttribute(k,v);
           });
            try {
             request.getRequestDispatcher(mv.getUrl())
                     .forward(request,resopnse);
            }
            catch (IOException|ServletException e){
                e.printStackTrace();
                throw new Exception("dispatch error!!! url="+mv.getUrl());
            }
       }else{
           resopnse.setContentType("application/json");
           resopnse.setCharacterEncoding("utf-8");
           PrintWriter printWriter = resopnse.getWriter();
           ObjectMapper objectMapper = new ObjectMapper();
           String s = objectMapper.writeValueAsString( result );
           printWriter.println(s);
           printWriter.flush();
           printWriter.close();
       }

    }
}
