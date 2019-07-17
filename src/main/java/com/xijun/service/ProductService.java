package com.xijun.service;


import java.util.List;

import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;
import com.xijun.dto.MesProductDto;
import com.xijun.model.MesProduct;
import com.xijun.param.MesProductVo;
import com.xijun.param.searchProductParam;

public interface ProductService {

	public void ProductBatchInsert(MesProductVo mesProductVo) ;

	public PageResult<MesProductDto> SelectAllProductBatchTo(searchProductParam param, PageQuery page);

	List<String> createProductIdsDefault(Long ocounts);

	Long getProductCount();

	public void productBatchStart(String ids);

	public PageResult<MesProductDto> productInList(searchProductParam param, PageQuery page);

	public PageResult<MesProductDto> productIronList(searchProductParam param, PageQuery page);

	public PageResult<MesProductDto> productBindList(searchProductParam param, PageQuery page);

	public MesProduct productSelectOne(String id);

	public PageResult<MesProductDto> productqueryBind(PageQuery page);

	public boolean productBindOper(MesProduct product, String weight, String id);

	public PageResult<MesProductDto> productqueryBindRele(MesProduct product, PageQuery page);

	public void productBindRele(MesProduct product, String weight, String id);

	public void updateProduct(MesProductVo mesProductVo);
	
}
