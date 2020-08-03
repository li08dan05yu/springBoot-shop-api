package com.fh.indent.service.order;

import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import java.util.List;

public interface OrderService {
    ServerResponse addOrder(List<Cart> cartList, Integer addressid, Integer paytype, Member member);

    void updateorderStatus(int status, String orderNo);
}
