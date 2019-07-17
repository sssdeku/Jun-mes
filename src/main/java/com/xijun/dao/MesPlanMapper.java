package com.xijun.dao;

import com.xijun.dto.MesPlanDto;
import com.xijun.model.MesPlan;

public interface MesPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MesPlan record);

    int insertSelective(MesPlan record);

    MesPlanDto selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MesPlan mesPlan);

    int updateByPrimaryKey(MesPlan record);

	
}