package com.urs.devops.filters;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@Component
public class ServiceFilter implements Filter {

    @Override
    public void  doFilter(ServletRequest req, ServletResponse var2, FilterChain var3) throws IOException, ServletException {
        String host = req.getRemoteHost();
        System.out.println("!!! Remote Host: " + host );
        var3.doFilter(req,var2);
    }
}
