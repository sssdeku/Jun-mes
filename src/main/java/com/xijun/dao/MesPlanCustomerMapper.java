package com.xijun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xijun.beans.PageQuery;
import com.xijun.dto.MesPlanDto;
import com.xijun.dto.SearchPlanDto;
import com.xijun.model.MesPlan;

public interface MesPlanCustomerMapper {

	public int countBySearchDto(@Param("dto") SearchPlanDto dto);

	public List<MesPlanDto> getPageListBySearchDto(@Param("dto") SearchPlanDto planDto, @Param("page")PageQuery page);
	
}
