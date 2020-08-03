package com.fh.cart.service;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.commodity.model.Commodity;
import com.fh.commodity.service.CommdityService;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CommdityService commdityService;
    @Override
    public ServerResponse addcart(Cart cart, HttpServletRequest request) {
        Member member = null;
        try {
            String usertoken = (String) request.getSession().getAttribute("usertoken");
            String userJson = URLDecoder.decode(usertoken, "utf-8");
            member = JSONObject.parseObject(userJson, Member.class);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Commodity commodity = commdityService.getUserById(cart.getComId());

        if(commodity==null){
            return ServerResponse.error("商品不存在");
        }
        if (commodity.getStatus()==2) {
            return ServerResponse.error("该商品已下架");
        }
        Boolean exist = RedisUtil.exist("user" + member.getId(), commodity.getId().toString());
        if(!exist){
            cart.setComName(commodity.getComName());
            cart.setPrice(commodity.getPrice());
            cart.setFilePath(commodity.getFilePath());
            cart.setMemId(member.getId());
            String jsonString = JSONObject.toJSONString(cart);
            RedisUtil.hset("user" + member.getId(), commodity.getId().toString(),jsonString);
        }else{
            String commJson = RedisUtil.hget("user" + member.getId(), commodity.getId().toString());
            Cart cartnew = JSONObject.parseObject(commJson,Cart.class);
            cartnew.setCount(cartnew.getCount()+cart.getCount());
            String jsonString = JSONObject.toJSONString(cartnew);
            RedisUtil.hset("user" + member.getId(), commodity.getId().toString(),jsonString);
        }


        return ServerResponse.success("添加成功");
    }


    @Override
    public ServerResponse queryList(HttpServletRequest request) {
        Member member = null;

        try {
            String usertoken = (String) request.getSession().getAttribute("usertoken");
            String userJson = URLDecoder.decode(usertoken, "utf-8");
            member = JSONObject.parseObject(userJson, Member.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<String> StringList = RedisUtil.hget("user" + member.getId());
        List<Cart> cartList = new ArrayList<Cart>();
        if(StringList !=null && StringList.size()>0){
            for (String str : StringList) {

                Cart cart = JSONObject.parseObject(str, Cart.class);

                cartList.add(cart);
            }
        }else{
            return ServerResponse.error("购物车为空 请添加");
        }

        return ServerResponse.success(cartList);
    }

    @Override
    public ServerResponse deleteAnimal(HttpServletRequest request, Integer comId) {
        Member member = null;

        try {
            String usertoken = (String) request.getSession().getAttribute("usertoken");
            String userJson = URLDecoder.decode(usertoken, "utf-8");
            member = JSONObject.parseObject(userJson, Member.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RedisUtil.hdel("user" + member.getId(), comId.toString());

        return ServerResponse.success();
    }

    @RequestMapping("queryCartProductCount")
    public ServerResponse  queryCartProductCount(Member member){
        //在session中获取用户信息(上面注解代表request获取session中的用户)
        // Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        //从redis中获取购物车信息
        List<String> stringList = RedisUtil.hget("user" + member.getId());
        long totalCount=0;
        if (stringList!=null && stringList.size()>0){

            for (String str : stringList) {
                Cart cart = JSONObject.parseObject(str, Cart.class);
                //购物车中的数量（新加的数量与之前的 总和）
                totalCount+=cart.getCount();
            }
        }else {
            //如果stringList为空则购物车中的数量为0
            return ServerResponse.success(0);
        }
        return ServerResponse.success(totalCount);
    }
}

