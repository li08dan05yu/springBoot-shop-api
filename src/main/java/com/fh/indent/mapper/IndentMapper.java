package com.fh.indent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.indent.model.Indent;
import com.fh.vo.IndentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndentMapper extends BaseMapper<Indent> {
    List<IndentVo> queryIndentList();
}
