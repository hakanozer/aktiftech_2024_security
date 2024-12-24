package com.works.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FilterConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        String[] freeUrl = {"/", "/login" };
        boolean loginStatus = true;
        for (String itemUrl : freeUrl) {
            if (url.equals(itemUrl)) {
                loginStatus = false;
                break;
            }
        }

        if (loginStatus) {
            // giriş zorunlu
            Object obj = request.getSession().getAttribute("customer");
            if (obj == null) {
                // giriş yok logine yönlendir
                response.sendRedirect("/");
            }else {
                // oturum var hizmeti sun
                filterChain.doFilter(request,response);
            }
        }else {
            filterChain.doFilter(request,response);
        }

    }

}
