package com.fh.indent.service.indent;

import com.fh.common.ServerResponse;
import com.fh.indent.mapper.IndentMapper;
import com.fh.vo.IndentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndentServiceImpl implements IndentService {


    @Resource
    private IndentMapper indentMapper;

    @Override
    public ServerResponse queryIndentList() {
        List<IndentVo> list = indentMapper.queryIndentList();
        return ServerResponse.success(list);
    }
}
