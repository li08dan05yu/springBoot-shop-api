package com.fh.commodity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@TableName("t_commodity")
public class Commodity {

    private Integer id;
    @TableField("comName")
    private String comName;
    @TableField("brandId")
    private Integer brandId;
    @TableField("price")
    private BigDecimal price;
    @TableField("status")
    private Integer status;
    @TableField("filePath")
    private String filePath;
    @TableField("createDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    @TableField("categoryId1")
    private Integer categoryId1;
    @TableField("categoryId3")
    private Integer categoryId3;
    @TableField("categoryId2")
    private Integer categoryId2;
    @TableField("isHot")
    private Integer isHot;
    @TableField("sales")
    private Integer sales;
    @TableField("inventory")
    private Integer inventory;

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Integer categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public Integer getCategoryId3() {
        return categoryId3;
    }

    public void setCategoryId3(Integer categoryId3) {
        this.categoryId3 = categoryId3;
    }

    public Integer getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Integer categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Commodity(Integer id, String comName, Integer brandId, BigDecimal price, Integer status, String filePath, Date createDate, Integer categoryId1, Integer categoryId3, Integer categoryId2, Integer isHot, Integer sales) {
        this.id = id;
        this.comName = comName;
        this.brandId = brandId;
        this.price = price;
        this.status = status;
        this.filePath = filePath;
        this.createDate = createDate;
        this.categoryId1 = categoryId1;
        this.categoryId3 = categoryId3;
        this.categoryId2 = categoryId2;
        this.isHot = isHot;
        this.sales = sales;
    }

    public Commodity() {
    }
}
