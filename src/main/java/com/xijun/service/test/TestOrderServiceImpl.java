package com.xijun.service.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xijun.dao.MesOrderMapper;
import com.xijun.model.MesOrder;

@Service
public class TestOrderServiceImpl implements TestOrderService{

	@Resource
	MesOrderMapper mesOrderMapper;
	@Override
	public void addOrder(MesOrder mesOrder) {
		// TODO Auto-generated method stub
		mesOrderMapper.insert(mesOrder);
	}
	@Override
	public MesOrder selectOne(String orderid) {
		// TODO Auto-generated method stub
		return mesOrderMapper.selectOne(orderid);
	}

}
