package com.xijun.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.xijun.model.MesPlan;

import com.xijun.param.SearchPlanParam;
import com.google.common.base.Preconditions;
import com.xijun.model.MesOrder;
import com.xijun.model.MesProduct;
import com.xijun.util.MyStringUtils;
import com.xijun.util.UUIDUtil;
import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;
import com.xijun.dao.MesOrderCustomerMapper;
import com.xijun.dao.MesOrderMapper;
import com.xijun.dao.MesPlanCustomerMapper;
import com.xijun.dao.MesPlanMapper;
import com.xijun.dao.MesProductMapper;
import com.xijun.dto.MesOrderDto;
import com.xijun.dto.MesPlanDto;
import com.xijun.dto.MesProductDto;
import com.xijun.dto.SearchPlanDto;
import com.xijun.exception.ParamException;
import com.xijun.service.PlanService;
import com.xijun.util.BeanValidator;

@Service
public class PlanServiceImpl implements PlanService{

	@Resource
	MesProductMapper mesProductMapper;
	@Resource
	MesPlanMapper mesPlanMapper;
	
	@Resource
	SqlSession sqlSession;
	
	@Resource
	MesOrderMapper mesOrderMapper;
	
	@Resource
	MesPlanCustomerMapper mesPlanCustomerMapper;
	
	@Resource
	MesOrderCustomerMapper mesOrderCustomerMapper;
	@Override
	public void prePlan(MesOrderDto mesOrderDto) {
				// 批量处理
				MesPlanMapper planMapper = sqlSession.getMapper(MesPlanMapper.class);
				MesPlan mesPlan =MesPlan.builder().planOrderid(mesOrderDto.getOrderId()).planProductname(mesOrderDto.getOrderProductname())//
						.planClientname(mesOrderDto.getOrderClientname()).planContractid(mesOrderDto.getOrderContractid()).planImgid(mesOrderDto.getOrderImgid())//
						.planMaterialname(mesOrderDto.getOrderMaterialname()).planCurrentstatus("计划").planCurrentremark("计划待执行").planSalestatus(mesOrderDto.getOrderSalestatus())//
						.planMaterialsource(mesOrderDto.getOrderMaterialsource()).planHurrystatus(mesOrderDto.getOrderHurrystatus()).planStatus(0).planCometime(mesOrderDto.getOrderCometime())//
						.planCommittime(mesOrderDto.getOrderCommittime()).planInventorystatus(mesOrderDto.getOrderInventorystatus()).build();
				mesPlan.setPlanOperator("user01");
				mesPlan.setPlanOperateIp("127.0.0.1");
				mesPlan.setPlanOperateTime(new Date());
				planMapper.insertSelective(mesPlan);
		
	}
	
	//批量启动order后的批量plan启动
		public void startPlansByOrderIds(String[] ids) {
			for(String tempId:ids) {
				Integer id=Integer.parseInt(tempId);
				MesOrderDto order=mesOrderMapper.selectByPrimaryKey(id);
				//查询内容非空 ，使用google的guava工具类
				Preconditions.checkNotNull(order);
				prePlan(order);
			}
		}

		@Override
		public PageResult<MesPlanDto> searchListPage(SearchPlanParam param, PageQuery page) {
			// 验证页码是否为空
			BeanValidator.check(page);
			// 将param中的字段传入dto进行数据层的交互
			// 自定义的数据模型，用来与数据库进行交互操作
			// searchDto 用于分页的where语句后面
			SearchPlanDto planDto = new SearchPlanDto();
			
			if(StringUtils.isNotBlank(param.getKeyword())) {
				planDto.setKeyword("%"+param.getKeyword()+"%");
			}
			if(StringUtils.isNotBlank(param.getSearch_msource())) {
				planDto.setSearch_msource(param.getSearch_msource());
			}
			if(StringUtils.isNotBlank(param.getSearch_status())) {
				planDto.setSearch_status(Integer.parseInt(param.getSearch_status()));
			}
			try {
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotBlank(param.getFromTime())) {
					planDto.setFromTime(format.parse(param.getFromTime()));
				}
				if(StringUtils.isNotBlank(param.getToTime())) {
					planDto.setToTime(format.parse(param.getToTime()));
				}
				
			}catch(Exception e) {
				throw new ParamException("传入的日期格式有问题");
			}
			
			int count  =  mesPlanCustomerMapper.countBySearchDto(planDto);
			if(count>0) {
				List<MesPlanDto> plans = mesPlanCustomerMapper.getPageListBySearchDto(planDto,page);
				return PageResult.<MesPlanDto>builder().total(count).data(plans).build();
			}
			return PageResult.<MesPlanDto>builder().build();
		}

		//批量启动计划
		@Override
		public void batchStartWithIds(String ids) {
			if(ids!=null && ids.length()>0) {
				MesPlanMapper mesPlanMapper = sqlSession.getMapper((MesPlanMapper.class));
				//ids=15&14&13,2019-07-01&2019-07-03
				String[] strs=ids.split(",");//strs[0]=15&14&13,strs[1]=2019-07-01&2019-07-03
				String[] idsTemp=strs[0].split("&");//idsTemp[0]=15,idsTemp[1]=14,idsTemp[2]=13
				String[] datesTemp=strs[1].split("&");//datesTemp[0]=2019-07-01,datesTemp[1]=2019-07-03
				String startTime=datesTemp[0];//startTime=2019-07-01
				String endTime=datesTemp[1];//endTime=2019-07-03
				
				for(int i=0;i<idsTemp.length;i++) {
					MesPlan mesPlan=new MesPlan();
					mesPlan.setId(Integer.parseInt(idsTemp[i]));
					mesPlan.setPlanWorkstarttime(MyStringUtils.string2Date(startTime,null));
					mesPlan.setPlanWorkendtime(MyStringUtils.string2Date(endTime,null));
					mesPlan.setPlanStatus(1);
					mesPlan.setPlanCurrentremark("计划已启动");
					
					mesPlanMapper.updateByPrimaryKeySelective(mesPlan);
					
					MesPlanDto mes = mesPlanMapper.selectByPrimaryKey(mesPlan.getId());
					
					
					//半成品材料 生成
					MesPlanDto plan=mesPlanMapper.selectByPrimaryKey(Integer.parseInt(idsTemp[i]));
					//产生半成品材料
					String orderid=plan.getPlanOrderid();
					MesOrder order=mesOrderCustomerMapper.selectByOrderId(orderid);
					//product
					MesProductDto mesProduct=MesProductDto.builder().productId(UUIDUtil.generateUUID())//
							.productOrderid(order.getId()).productPlanid(plan.getId())//
							.productMaterialname(order.getOrderMaterialname())//
							.productImgid(order.getOrderImgid())//
							.productMaterialsource(order.getOrderMaterialsource())//\
							.productStatus(0).build();
					mesProduct.setProductOperateIp("127.0.0.1");
					mesProduct.setProductOperator("user01");
					mesProduct.setProductOperateTime(new Date());
					mesProductMapper.insertSelective(mesProduct);
				}
				
			}
			
		}

}
