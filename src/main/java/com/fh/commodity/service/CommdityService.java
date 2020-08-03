package com.fh.commodity.service;

import com.fh.commodity.model.Commodity;
import com.fh.common.ServerResponse;
import com.fh.vo.CommodityVo;

public interface CommdityService {
    ServerResponse queryCommodityList();

    ServerResponse queryCommodityPage(CommodityVo commodityVo);

    ServerResponse queryCommodityAll();

    Commodity getUserById(Integer comId);

    Long updateinvent(Integer comId, Integer count);
}
