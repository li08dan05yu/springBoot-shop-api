package com.fh.resolver;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.MemberAnnotation;
import com.fh.member.model.Member;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class MemberResolver  implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(MemberAnnotation.class)){
            return true;
        }
        return false;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        //吧用户信息放入session中
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Member member = null;

        try {
            String usertoken = (String) request.getSession().getAttribute("usertoken");
            String userJson = URLDecoder.decode(usertoken, "utf-8");
            member = JSONObject.parseObject(userJson, Member.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return member;
    }
}
