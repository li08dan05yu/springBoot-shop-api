package com.fh.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.member.service.MemberService;
import com.fh.util.AliyunSmsUtils;
import com.fh.util.JwtUtil;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping("member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Ignore
    @RequestMapping("getUserByName")
    public ServerResponse getUserByName(String name){
        Member member = memberService.getUserByName(name);
        if(member==null){
            return  ServerResponse.success();
        }
        return  ServerResponse.error();
    }
    @Ignore
    @RequestMapping("getUserByphone")
    public ServerResponse getUserByphone(String phone){
        Member member = memberService.getUserByphone(phone);
        if(member==null){
            return  ServerResponse.success();
        }
        return  ServerResponse.error();
    }
    @Ignore
    @RequestMapping("getcode")
    public ServerResponse getcode(String phone){
        String newcode = JSON.toJSONString(AliyunSmsUtils.getNewcode());
        System.out.println(newcode);
        try {
                SendSmsResponse  sendSmsResponse = AliyunSmsUtils.sendSms(phone, newcode);
                if (sendSmsResponse.getCode()!= null && "OK".equals(sendSmsResponse.getCode())){
                    RedisUtil.setex(phone,3000,newcode);
                    return ServerResponse.success();
                }

        } catch (ClientException e) {
            e.printStackTrace();
            return ServerResponse.error(e.getErrMsg());
        }
        return null;
    }

    @Ignore
    @RequestMapping("addUser")
    public ServerResponse addUser(Member member){

        return memberService.addUser(member);
    }
    @Ignore
    @RequestMapping("longin")
    public ServerResponse longin(Member member){

        return memberService.longin(member);
    }

    @RequestMapping("quit")
    public ServerResponse quit(HttpServletRequest request){
        String token = request.getHeader("x-auth");
        RedisUtil.del(token);

        return ServerResponse.success();
    }


}
