package com.fh.member.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.member.mapper.MemberMapper;
import com.fh.member.model.Member;
import com.fh.util.JwtUtil;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public Member getUserByName(String name) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Member getUserByphone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",phone);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public ServerResponse addUser(Member member) {
            if(!RedisUtil.get(member.getPhone()).equals(member.getCode())){
               return ServerResponse.error("请检查验证码是否正确");
            }
        memberMapper.insert(member);
            return ServerResponse.success();
    }

    @Override
    public ServerResponse longin(Member member) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",member.getName());
        Member memberDB = memberMapper.selectOne(queryWrapper);
        if(memberDB==null){
            return ServerResponse.success("该用户不存在");
        }

        if (!RedisUtil.get(member.getPhone()).equals(member.getCode())){
            return ServerResponse.success("验证码不正确 请确认");
        }
        if (!memberDB.getPassword().equals(member.getPassword())){
            return ServerResponse.success("密码不正确 请确认");
        }
        String token = null;
        try {
            String jsonString = JSONObject.toJSONString(memberDB);
            String encodeJSON = URLEncoder.encode(jsonString, "utf-8");
            token = JwtUtil.sign(encodeJSON);
            RedisUtil.setex(token,30*60*1000,jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return ServerResponse.success(token);
    }

}
