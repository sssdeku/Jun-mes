package com.xijun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.xijun.beans.PageQuery;
import com.xijun.dto.MesProductDto;
import com.xijun.dto.ProductBindOperDto;
import com.xijun.dto.searchProductDto;

public interface MesProductCustomerMapper {

	public List<MesProductDto> getProductBachList(@Param("dto")searchProductDto dto,@Param("page") PageQuery page);

	public Long getProductCount();

	public int getProductCountsBySomeList(@Param("dto") searchProductDto dto);

	public void updateStatus(String id);

	public void updateProductRealWeight(@Param("dto")ProductBindOperDto pdto1);

	public void updateProductBakWeight(@Param("dto")ProductBindOperDto pdto2);

	public List<MesProductDto> queryBindRele(@Param("dto")ProductBindOperDto pdto,@Param("page") PageQuery page);

	public int queryBindMuch(@Param("dto")ProductBindOperDto pdto);

	public void LupdateStatus(String id);

	public void LupdateProductBakWeight(@Param("dto")ProductBindOperDto pdto);

	public void LupdateProductRealWeight(@Param("dto")ProductBindOperDto pdto1);

	public int getProductCountsBySomeListth(@Param("dto")searchProductDto dto); 

	public List<MesProductDto> getProductBachListth(@Param("dto")searchProductDto dto,@Param("page") PageQuery page);
	
}
