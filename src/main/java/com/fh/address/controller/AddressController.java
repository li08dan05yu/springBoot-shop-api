package com.fh.address.controller;

import com.fh.address.service.AddressService;
import com.fh.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("address")
public class AddressController {


    @Autowired
    private AddressService addressService;

    @RequestMapping("queryList")
    @ResponseBody
    public ServerResponse queryList(){


        return addressService.queryList();
    }

}
