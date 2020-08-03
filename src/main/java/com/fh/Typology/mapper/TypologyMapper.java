package com.fh.Typology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.Typology.model.Typology;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface TypologyMapper extends BaseMapper<Typology> {

    List<Map<String,Object>> queryTypology();


}
