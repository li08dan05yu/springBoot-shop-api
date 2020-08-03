package com.fh.indent.service.order;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.commodity.model.Commodity;
import com.fh.commodity.service.CommdityService;
import com.fh.common.ServerResponse;
import com.fh.indent.mapper.IndentMapper;
import com.fh.indent.mapper.OrderMapper;
import com.fh.indent.model.Indent;
import com.fh.indent.model.Order;
import com.fh.member.model.Member;
import com.fh.util.BigDecimalUtil;
import com.fh.util.IdUtil;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private CommdityService commdityService;
    @Resource
    private IndentMapper indentMapper;


    @Override
    public ServerResponse addOrder(List<Cart> cartList, Integer addressid, Integer paytype, Member member) {

        //生成订单编号
        String orderid = IdUtil.createId();
        //商品总价格
        BigDecimal totalPrice = new BigDecimal("0.00");
        //订单详情
        List<Indent> indentList = new ArrayList<>();
        //库存不足
        List list = new ArrayList();
        Commodity comm =null;
        for (Cart cart : cartList) {
            comm = commdityService.getUserById(cart.getComId());
            //判断商品状态
            if(comm.getStatus()==2){
                return ServerResponse.error(comm.getComName()+"已下架");
            }else{
                //查询订单商品 判断库存是否充足
                if(cart.getCount()>comm.getInventory()){
                    list.add(cart.getComName());
                }
                //订单充足的情况下 减库存
                Long res = commdityService.updateinvent(cart.getComId(),cart.getCount());
                if(res==1){
                    Indent indent = new Indent();
                    indent.setCommid(cart.getComId());
                    indent.setCount(cart.getCount());
                    indent.setFilepath(cart.getFilePath());
                    indent.setName(cart.getComName());
                    indent.setPrice(cart.getPrice());
                    indent.setOrderid(orderid);

                    indentList.add(indent);
                    BigDecimal subTotal = BigDecimalUtil.mul(cart.getPrice().toString(),cart.getCount()+"");
                    totalPrice = BigDecimalUtil.add(totalPrice,subTotal);
                }else{
                    list.add(cart.getComName());
                }

            }
        }
            if (indentList!=null && indentList.size()==cartList.size()){
                for (Indent indent : indentList) {
                    indentMapper.insert(indent);
                    String cartstr = RedisUtil.hget("user" + member.getId(), comm.getId().toString());
                    if (StringUtils.isNotEmpty(cartstr)){
                        Cart cart1 = JSONObject.parseObject(cartstr, Cart.class);

                        if(cart1.getCount()<=indent.getCount()){
                            RedisUtil.hdel("user" + member.getId(), comm.getId().toString());
                        }else{
                            long aaa =  cart1.getCount() - indent.getCount();
                            int bbb = (int) aaa;
                            cart1.setCount(bbb);
                            RedisUtil.hset("user" + member.getId(), comm.getId().toString(),cart1.toString());
                        }

                    }



                }

                Order order = new Order();
                order.setTotalprice(totalPrice);
                order.setAddressid(addressid);
                order.setCreatedate(new Date());
                order.setId(orderid);
                order.setPaytype(paytype);
                order.setMemberid(member.getId());
                order.setStatus(2);
                orderMapper.insert(order);




                return ServerResponse.success(orderid);
            }else{
                return ServerResponse.error(list);
            }

    }

    @Override
    public void updateorderStatus(int status, String orderNo) {
        Order order = orderMapper.selectById(orderNo);
        order.setStatus(status);
        orderMapper.updateById(order);
    }
}
