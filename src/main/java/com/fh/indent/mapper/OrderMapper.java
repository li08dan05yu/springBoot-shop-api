package com.fh.indent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.indent.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
