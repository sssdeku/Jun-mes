package com.xijun.dao;

import com.xijun.dto.MesOrderDto;
import com.xijun.model.MesOrder;

public interface MesOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MesOrder record);

    int insertSelective(MesOrderDto record);

    MesOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MesOrder record);

    int updateByPrimaryKey(MesOrder record);

	MesOrder selectOne(String orderid);
}