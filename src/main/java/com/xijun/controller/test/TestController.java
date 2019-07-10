package com.xijun.controller.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xijun.model.MesOrder;
import com.xijun.service.test.TestOrderService;

@Controller
public class TestController {

	@Resource
	TestOrderService testOrderService;
	
	
/*	@RequestMapping("addOrder")
	public ModelAndView addOrder(HttpServletRequest request, HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		
		MesOrder mesOrder=new MesOrder();
		System.out.println("zzz");
		
		mesOrder.setOrderId(orderId);
		testOrderService.addOrder(mesOrder);
		
		System.out.println(mesOrder.getOrderId());
		
		MesOrder mo = testOrderService.selectOne(mesOrder.getOrderId());
		if(mo!=null) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("order", mesOrder);
			mv.setViewName("success");
			return mv;
			
		}
		return null;
	}
	*/
	
}
