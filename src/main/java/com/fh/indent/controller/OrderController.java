package com.fh.indent.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.address.model.Address;
import com.fh.cart.model.Cart;
import com.fh.commodity.model.Commodity;
import com.fh.common.Idempotent;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.indent.service.order.OrderService;
import com.fh.member.model.Member;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("addOrder")
    @ResponseBody
    @Idempotent
    public ServerResponse addOrder(@Param("jsonstr") String jsonstr, @Param("addressid") Integer addressid, @Param("paytype") Integer paytype, @MemberAnnotation Member member){

        if(StringUtils.isNotEmpty(jsonstr)){
            List<Cart> cartList = JSONObject.parseArray(jsonstr, Cart.class);
            return orderService.addOrder(cartList,addressid,paytype,member);
        }
        return ServerResponse.error("请选择商品");


    }

    @RequestMapping("getTotal")
    @ResponseBody
    public ServerResponse getTotal(){

        String mtoken = UUID.randomUUID().toString();

        RedisUtil.setex("mtoken",3000,mtoken);
        return ServerResponse.success(mtoken);

    }

}
