package com.xijun.service;

import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;
import com.xijun.dto.MesOrderDto;
import com.xijun.dto.MesPlanDto;
import com.xijun.param.MesOrderVo;
import com.xijun.param.SearchPlanParam;
public interface PlanService {
	
	public void prePlan(MesOrderDto mesOrderDto);
	
	public void startPlansByOrderIds(String[] ids);

	public PageResult<MesPlanDto> searchListPage(SearchPlanParam spp, PageQuery page);

	public void batchStartWithIds(String ids);

}
