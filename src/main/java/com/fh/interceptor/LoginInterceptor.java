package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.Ignore;
import com.fh.common.MyException;
import com.fh.member.model.Member;
import com.fh.util.JwtUtil;
import com.fh.util.RedisUtil;
import org.springframework.http.HttpHeaders;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //处理客户端传过来的自定义头信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,mtoken,content-type");
        //处理客户端发过来的put，delete
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"PUT,POST,DELETE,GET");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断方法上是否存在 @Ignore注解 如果存在 则该方法不需要进行拦截
        if (method.isAnnotationPresent(Ignore.class)){
            return true;
        }
        //【request请求】 获取请求头中的token
        String token = request.getHeader("x-auth");
        //如果没有token 则跳转到登陆页面
        if (StringUtils.isEmpty(token)){
            //抛出异常
            throw new LoginException();
        }
        //验证是否过期
        boolean exist = RedisUtil.exist(token);
        if(!exist){
            throw new LoginException();
        }

        //验证token
        boolean res = JwtUtil.verify(token);
        //当res == true是进入 else 抛出异常
        if (res){
            //获取token
            String userString = JwtUtil.getUser(token);

            request.getSession().setAttribute("usertoken",userString);


            //解析token
            String userJson = URLDecoder.decode(userString, "utf-8");
            Member member = JSONObject.parseObject(userJson, Member.class);
        }else {
            throw new LoginException();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/static/");
    }
}
