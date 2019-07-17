package com.xijun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;
import com.xijun.common.JsonData;
import com.xijun.dto.MesPlanDto;
import com.xijun.model.MesPlan;
import com.xijun.param.SearchPlanParam;
import com.xijun.service.PlanService;

@Controller
@RequestMapping("/plan")
public class PlanController {

	
	@Resource
	private PlanService planService;
	
	
	private static final String PATH="plan/";
	
	//页面跳转到未启动计划
	@RequestMapping("/plan.page")
	public String plan() {
		return PATH+"plan";
	}
	
	//跳转到已启动计划页面
		@RequestMapping("planStarted.page")
		public String planStarted() {
			return PATH+"planStarted";
	}
	
	//分页显示未启动计划
	@ResponseBody
	@RequestMapping("/plan.json")
	public JsonData searchPage(SearchPlanParam param,PageQuery page) {
		PageResult<MesPlanDto> plans = planService.searchListPage(param,page);
		return JsonData.success(plans);
	}
	//批量启动计划
	@ResponseBody
	@RequestMapping("/planBatchStart.json")
	public JsonData planBatchStart(String ids) {
		
		System.out.println("sssssssssssssssssssssssssssssssssssssssssssss");
		System.out.println(ids);
		planService.batchStartWithIds(ids);
		return JsonData.success();
	}
	
	
}
