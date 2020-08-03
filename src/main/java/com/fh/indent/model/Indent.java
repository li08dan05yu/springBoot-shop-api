package com.fh.indent.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
@TableName("t_indent")
public class Indent {
    @TableId(type = IdType.AUTO)
    private Integer id;//订单id

    private String orderid;//订单编号

    private String name;//商品名称

    private String filepath;//图片路径

    private Integer commid;//商品id

    private long count;//商品数量

    private BigDecimal price;//商品价格

    private BigDecimal subtotalprice;//商品总价


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getCommid() {
        return commid;
    }

    public void setCommid(Integer commid) {
        this.commid = commid;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotalprice() {
        return subtotalprice;
    }

    public void setSubtotalprice(BigDecimal subtotalprice) {
        this.subtotalprice = subtotalprice;
    }
}
