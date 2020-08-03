package com.fh.cart.controller;

import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("addcart")
    @ResponseBody
    public ServerResponse addcart(Cart cart, HttpServletRequest request){

        return cartService.addcart(cart,request);
    }

    @RequestMapping("queryList")
    @ResponseBody
    public ServerResponse queryList(HttpServletRequest request){


        return cartService.queryList(request);
    }

    @RequestMapping("deleteAnimal")
    @ResponseBody
    public ServerResponse deleteAnimal(Integer comId,HttpServletRequest request){


        return cartService.deleteAnimal(request,comId);
    }

    @RequestMapping("queryCartProductCount")
    @ResponseBody
    public ServerResponse queryCartProductCount(@MemberAnnotation Member member){


        return cartService.queryCartProductCount(member);
    }
}


