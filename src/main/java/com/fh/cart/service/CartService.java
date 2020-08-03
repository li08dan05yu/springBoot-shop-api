package com.fh.cart.service;

import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    ServerResponse addcart(Cart cart, HttpServletRequest request);

    ServerResponse queryList(HttpServletRequest request);

    ServerResponse deleteAnimal(HttpServletRequest request, Integer comId);

    ServerResponse queryCartProductCount(Member member);
}
