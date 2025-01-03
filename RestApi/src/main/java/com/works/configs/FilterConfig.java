package com.works.configs;

import com.works.entities.Info;
import com.works.repositories.InfoRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FilterConfig implements Filter {

    final InfoRepository infoRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        String roles = "";
        if (auth != null) {
            username = auth.getName();
            roles = auth.getAuthorities().toString();
        }
        String url = request.getRequestURI();
        String time = System.currentTimeMillis() + "";
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String sessionId = request.getSession().getId();

        Info i = new Info(username, roles, url, time, userAgent, ip, sessionId);
        infoRepository.save(i);

        filterChain.doFilter(request, response);
    }

}
