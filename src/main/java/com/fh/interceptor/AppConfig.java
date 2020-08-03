package com.fh.interceptor;

import com.fh.resolver.MemberResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport {

    //参数解析器
    @Resource
    public MemberResolver memberResolver;

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public OrderInterceptor orderInterceptor(){
        return new OrderInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        //拦截所有路径
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(orderInterceptor()).addPathPatterns("/**");
    }

    //参数解析器
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(memberResolver);
    }
}
