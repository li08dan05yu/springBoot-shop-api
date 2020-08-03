package com.fh.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.commodity.model.Commodity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommdityMapper extends BaseMapper<Commodity> {


    Long updateinvent(Integer comId, Integer inventory);
}
