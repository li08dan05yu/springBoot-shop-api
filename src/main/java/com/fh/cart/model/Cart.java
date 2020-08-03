package com.fh.cart.model;

import java.math.BigDecimal;

public class Cart {
    private Integer memId;
    private Integer comId;
    private String comName;
    private BigDecimal price;
    private String filePath;
    private Integer count;

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "comId=" + comId +
                ", comName='" + comName + '\'' +
                ", price=" + price +
                ", filePath='" + filePath + '\'' +
                ", count=" + count +
                '}';
    }
}
