package com.fh.interceptor;

import com.fh.common.Idempotent;
import com.fh.common.OrderException;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class OrderInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断方法上是否存在 @Idempotent 如果存在 则该方法需要进行拦截
        if (!method.isAnnotationPresent(Idempotent.class)){
            return true;
        }
        //【request请求】 获取请求头中的mtoken
        String mtoken = request.getHeader("mtoken");
        //如果没有token 则不需要检查幂等性
        if (StringUtils.isEmpty(mtoken)){
            //抛出异常
            throw new OrderException("没有mtoken");
        }

        boolean exist = RedisUtil.exist("mtoken");
        if(!exist){
            throw new OrderException("mtoken失效");
        }

        Long res = RedisUtil.dell("mtoken");
        if (res==0){
            throw new OrderException("订单重复了呦");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
