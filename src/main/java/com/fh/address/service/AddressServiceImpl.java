package com.fh.address.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.address.mapper.AddressMapper;
import com.fh.address.model.Address;
import com.fh.common.ServerResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public ServerResponse queryList() {
        Address address = new Address();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("statr");
        List list = addressMapper.selectList(queryWrapper);
        return ServerResponse.success(list);
    }
}
