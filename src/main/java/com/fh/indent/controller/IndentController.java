package com.fh.indent.controller;

import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.indent.service.indent.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("indent")
public class IndentController {
    @Autowired
    private IndentService indentService;


    @RequestMapping("queryIndentList")
    @ResponseBody
    @Ignore
    public ServerResponse queryIndentList(){


        return  indentService.queryIndentList();
    }
}
