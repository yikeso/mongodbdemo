package com.china.ciic.mongodemo.interceptors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截A用户名访问B用户名的userspace
 */
public class UserspaceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        UserDetails userDetails =null;
        try {
            //未登录拦截
            userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
        }catch (Exception e){
            httpServletResponse.reset();
            httpServletResponse.sendRedirect("/login");
            return false;
        }
        String username = userDetails.getUsername();
        String uri = httpServletRequest.getRequestURI();
        username = "/u/" + username;
        //用户名与访问的userspace不一致拦截
        if(uri.startsWith(username)){
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
