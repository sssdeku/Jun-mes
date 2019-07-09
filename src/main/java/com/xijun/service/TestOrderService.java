package com.xijun.service;

import org.springframework.stereotype.Service;

import com.xijun.model.MesOrder;
@Service
public interface TestOrderService {

	public void addOrder(MesOrder mesOrder);
	public MesOrder selectOne(String orderid);
}
