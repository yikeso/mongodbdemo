package com.china.ciic.mongodemo.configs;

import com.china.ciic.mongodemo.interceptors.UserspaceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器控制
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserspaceInterceptor()).addPathPatterns("/u/**");
        super.addInterceptors(registry);
    }
}
