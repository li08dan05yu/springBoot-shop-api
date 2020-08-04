package com.fh.commodity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.commodity.mapper.CommdityMapper;
import com.fh.commodity.model.Commodity;
import com.fh.common.ServerResponse;
import com.fh.vo.CommodityVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommdityServiceImpl implements CommdityService {

    @Resource
    private CommdityMapper commdityMapper;

    @Override
    public ServerResponse queryCommodityList() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("isHot",1);
        List list = commdityMapper.selectList(queryWrapper);
        return ServerResponse.success(list);
    }

    @Override
    public ServerResponse queryCommodityPage(CommodityVo commodityVo) {

       // QueryWrapper queryWrapper = new QueryWrapper();
        IPage<Commodity> page = new Page<Commodity>();
        page.setCurrent(commodityVo.getStatr());
        page.setSize(commodityVo.getPageSize());

        IPage<Commodity> teacherIPage = commdityMapper.selectPage(page, null);

        List<Commodity> list = teacherIPage.getRecords();
        long total = teacherIPage.getTotal();

        Map map = new HashMap();
        map.put("list",list);
        map.put("total",total);
        return ServerResponse.success(map);
    }

    @Override
    public ServerResponse queryCommodityAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        List list = commdityMapper.selectList(queryWrapper);
        return ServerResponse.success(list);
    }

    @Override
    public Commodity getUserById(Integer comId) {
        return  commdityMapper.selectById(comId);
    }

    @Override
    public Long updateinvent(Integer comId, Integer count) {

        return  commdityMapper.updateinvent(comId,count);
    }


}
