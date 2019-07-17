package com.xijun.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;
import com.xijun.param.SearchOrderParam;
import com.xijun.common.JsonData;
import com.xijun.model.MesOrder;
import com.xijun.param.MesOrderVo;
import com.xijun.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderService orderService;
	
	private static final String PATCH="order/";
	
	//跳转未启动订单页面
	@RequestMapping("/orderBatch.page")
	public String orderBatch() {
		return PATCH+"orderBatch";
	}
	
	//添加MesOrder
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		
		orderService.orderBatchInserts(mesOrderVo);
		return JsonData.success();
	}
	
	//分页显示MesOrder数据
	@ResponseBody
	@RequestMapping("/order.json")
	public JsonData searchPage(SearchOrderParam param, PageQuery page) {
		PageResult<MesOrder> pr = (PageResult<MesOrder>) orderService.searchPageList(param, page);
		return JsonData.success(pr);
	}
	
		//批量启动订单
		@ResponseBody
		@RequestMapping("/orderBatchStart.json")
		public JsonData orderBatchStart(String ids) {
			orderService.batchStart(ids);
			return JsonData.success();
		}
		//跳转已启动订单页面
		
		@RequestMapping("/order.page")
		public String page() {
			return PATCH+"order";
		}
	
}
