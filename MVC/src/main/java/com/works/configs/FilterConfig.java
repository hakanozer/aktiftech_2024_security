package com.works.configs;

import com.works.entities.Customer;
import com.works.entities.Info;
import com.works.repositories.InfoRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FilterConfig implements Filter {

    final InfoRepository infoRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String sessionID = request.getSession().getId();
        String userAgent = request.getHeader("User-Agent");
        String time = System.currentTimeMillis() + "";
        String email = "";
        long cid = 0;
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

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
                Customer customer = (Customer) obj;
                email = customer.getEmail();
                cid = customer.getCid();
                request.setAttribute("customer", customer);
                filterChain.doFilter(request,response);
            }
        }else {
            filterChain.doFilter(request,response);
        }

        // logs
        // url, sessionID, userAgent, time, email, cid, ip
        Info i = new Info(url, sessionID, userAgent, time, email,cid, ip);
        infoRepository.save(i);

    }

}
