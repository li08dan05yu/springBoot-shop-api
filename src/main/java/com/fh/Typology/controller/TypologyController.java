package com.fh.Typology.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.Typology.service.TypologyService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("typology")
@CrossOrigin
@Api(value = "desc of class")
public class TypologyController {

    @Resource
    private TypologyService typologyService;

    @RequestMapping("queryTypology")
    @ResponseBody
    @Ignore
    @ApiOperation(value = "查询所有类型")
    public ServerResponse queryCategory(){
        boolean typologyList = RedisUtil.exist("typologyList");
        if(typologyList){
            String typologyList1 = RedisUtil.get("typologyList");
            List<Map> maps = JSONObject.parseArray(typologyList1, Map.class);
            return ServerResponse.success(maps);
        }
        List<Map<String, Object>> list = typologyService.queryTypology();
        String jsonString = JSONObject.toJSONString(list);
        RedisUtil.set("typologyList",jsonString);
        return ServerResponse.success(list);
    }


}
