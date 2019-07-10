package com.xijun.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xijun.common.JsonData;
import com.xijun.param.MesOrderVo;
import com.xijun.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderService orderService;
	private static final String PATCH="order/";
	@RequestMapping("/orderBatch.page")
	public String orderBatch() {
		return PATCH+"orderBatch";
	}
	
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		
		orderService.orderBatchInserts(mesOrderVo);
		return JsonData.success();
	}
	
}
