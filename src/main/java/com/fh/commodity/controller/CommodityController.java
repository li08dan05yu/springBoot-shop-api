package com.fh.commodity.controller;

import com.fh.commodity.service.CommdityService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.vo.CommodityVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("commodity")
public class CommodityController {

    @Resource
    private CommdityService commdityService;

    @RequestMapping("queryCommodityList")
    @ResponseBody
    public ServerResponse queryCommodityList(){

        return commdityService.queryCommodityList();
    }
    @RequestMapping("queryCommodityPage")
    @ResponseBody
    public ServerResponse queryCommodityPage(CommodityVo commodityVo){

        return commdityService.queryCommodityPage(commodityVo);
    }
    @RequestMapping("queryCommodityAll")
    @ResponseBody
    public ServerResponse queryCommodityAll(){

        return commdityService.queryCommodityAll();
    }
}
