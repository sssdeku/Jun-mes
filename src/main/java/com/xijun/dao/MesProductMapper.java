package com.xijun.dao;

import com.xijun.dto.MesProductDto;
import com.xijun.model.MesProduct;

public interface MesProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MesProduct record);

    int insertSelective(MesProductDto pdto);

    MesProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MesProduct record);

    int updateByPrimaryKey(MesProduct record);
}