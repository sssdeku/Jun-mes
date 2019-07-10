package com.xijun.service;

import java.util.List;
import com.xijun.param.MesOrderVo;
public interface OrderService {

	public void batchStart(String ids);
	
	public void update(MesOrderVo mesOrderVo);
	
	public void addOrder(MesOrderVo mesOrderVo);
	
	public void orderBatchInserts(MesOrderVo mesOrderVo);
	
	// 获取数据库所有的数量
	public Long getOrderCount();
	
	// 获取id集合
	public List<String> createOrderIdsDefault(Long ocounts);
	
	
}
