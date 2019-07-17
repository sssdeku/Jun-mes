package com.xijun.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.xijun.dao.MesProductMapper;
import com.xijun.dto.MesProductDto;
import com.xijun.dto.ProductBindOperDto;
import com.xijun.dto.searchProductDto;
import com.xijun.exception.SysMineException;
import com.xijun.model.MesProduct;
import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;

import com.xijun.dao.MesProductCustomerMapper;

import com.xijun.param.MesProductVo;
import com.xijun.param.searchProductParam;
import com.xijun.service.ProductService;

import com.xijun.util.BeanValidator;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	private MesProductMapper mesProductMapper;

	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;
	
	@Resource
	private SqlSession sqlSession;
	
	// 一开始就定义一个id生成器
	private IdGenerator ig = new IdGenerator();
	
	//批量添加材料
	@Override
	public void ProductBatchInsert(MesProductVo mesProductVo) {

		BeanValidator.check(mesProductVo);// beanvalidator是什么，怎么用
		
		Integer counts = mesProductVo.getCount();
			// 根据counts的个数，生成需要添加的ids的数据集合
		List<String> ids = createProductIdsDefault(Long.valueOf(counts));

		MesProductMapper mesProductMapper = sqlSession.getMapper(MesProductMapper.class);
		for(String productId:ids) {
			
			try {
			MesProductDto pdto = MesProductDto.builder().productId(productId)
					.productImgid(mesProductVo.getProductImgid()).productRealweight(mesProductVo.getProductRealweight())
					.productIrontype(mesProductVo.getProductIrontype()).productIrontypeweight(mesProductVo.getProductIrontypeweight())
					.productLeftweight(mesProductVo.getProductLeftweight()).productMaterialname(mesProductVo.getProductMaterialname())
					.productMaterialsource(mesProductVo.getProductMaterialsource()).productTargetweight(mesProductVo.getProductTargetweight())
					.productStatus(mesProductVo.getProductStatus()).productRemark(mesProductVo.getProductRemark())
					.productHeatNumber(mesProductVo.getProductHeatNumber()).build();
					
					pdto.setProductBakweight(mesProductVo.getProductLeftweight());
					pdto.setProductOperator("jack");
					pdto.setProductOperateTime(new Date());
					pdto.setProductOperateIp("128.169.0.1");
					
					mesProductMapper.insertSelective(pdto);
					
			}catch(Exception e) {
				e.printStackTrace();
				throw new SysMineException(e + "添加单个订单出了问题");
			}
		}
	}
	
	//查询可以倒库的所有材料（批量倒库页面展示）
	@Override
	public PageResult<MesProductDto> SelectAllProductBatchTo(searchProductParam param, PageQuery page) {
		
		BeanValidator.check(page);
		
		searchProductDto dto = new searchProductDto();
		
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getProductMaterialsource())) {
			dto.setProductMaterialsource(param.getProductMaterialsource());
		}
		if(param.getProductHeatNumber()!=null) {
			dto.setProductHeatNumber(param.getProductHeatNumber());
		}
		dto.setProductStatus(0);
		
		int counts = mesProductCustomerMapper.getProductCountsBySomeListth(dto);
		
		if(counts > 0) {
			
			
			List<MesProductDto> pds = mesProductCustomerMapper.getProductBachListth(dto,page);
			
			return PageResult.<MesProductDto>builder().data(pds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Long getProductCount() {
		return mesProductCustomerMapper.getProductCount();
	}

	@Override
	public List<String> createProductIdsDefault(Long ocounts) {
		if (ig == null) {
			ig = new IdGenerator();
		}

		ig.setCurrentdbidscount(getProductCount());
		List<String> list = ig.initIds(ocounts);
		ig.clear();
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// 1 默认生成代码
	// 2 手工生成代码
	// id生成器
	class IdGenerator {
		// 数量起始位置
		private Long currentdbidscount;
		private List<String> ids = new ArrayList<String>();
		private String idpre;
		private String yearstr;
		private String idafter;

		public IdGenerator() {
		}

		public Long getCurrentdbidscount() {
			return currentdbidscount;
		}

		public void setCurrentdbidscount(Long currentdbidscount) {
			this.currentdbidscount = currentdbidscount;
			if (null == this.ids) {
				this.ids = new ArrayList<String>();
			}
		}

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		public String getIdpre() {
			return idpre;
		}

		public void setIdpre(String idpre) {
			this.idpre = idpre;
		}

		public String getYearstr() {
			return yearstr;
		}

		public void setYearstr(String yearstr) {
			this.yearstr = yearstr;
		}

		public String getIdafter() {
			return idafter;
		}

		public void setIdafter(String idafter) {
			this.idafter = idafter;
		}

		public List<String> initIds(Long ocounts) {
			for (int i = 0; i < ocounts; i++) {
				this.ids.add(getIdPre() + yearStr() + getIdAfter(i));
			}
			return this.ids;
		}

		//
		private String getIdAfter(int addcount) {
			// 系统默认生成5位 ZX1700001
			int goallength = 6;
			// 获取数据库order的总数量+1+循环次数(addcount)
			int count = this.currentdbidscount.intValue() + 1 + addcount;
			StringBuilder sBuilder = new StringBuilder("");
			// 计算与5位数的差值
			int length = goallength - new String(count + "").length();
			for (int i = 0; i < length; i++) {
				sBuilder.append("0");
			}
			sBuilder.append(count + "");
			return sBuilder.toString();
		}

		private String getIdPre() {
			// idpre==null?this.idpre="ZX":this.idpre=idpre;
			this.idpre = "ZX_P_";
			return this.idpre;
		}

		private String yearStr() {
			Date currentdate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yearstr = sdf.format(currentdate).substring(2, 4);
			return yearstr;
		}

		public void clear() {
			this.ids = null;
		}

		@Override
		public String toString() {
			return "IdGenerator [ids=" + ids + "]";
		}
	}


	@Override
	public void productBatchStart(String ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&ids.length()>0) {
			String[] idss = ids.split("&");
			for(String id:idss) {
				mesProductCustomerMapper.updateStatus(id);
			}
			
		}
	}

	//查询已倒库的材料（已倒库页面数据展示）
	@Override
	public PageResult<MesProductDto> productInList(searchProductParam param, PageQuery page) {
		BeanValidator.check(page);
		
		searchProductDto dto = new searchProductDto();
		
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getProductMaterialsource())) {
			dto.setProductMaterialsource(param.getProductMaterialsource());
		}
		if(param.getProductHeatNumber()!=null) {
			dto.setProductHeatNumber(param.getProductHeatNumber());
		}
		
		dto.setProductStatus(1);
		int counts = mesProductCustomerMapper.getProductCountsBySomeListth(dto);
		
		if(counts > 0) {
			
		
			List<MesProductDto> pds = mesProductCustomerMapper.getProductBachListth(dto,page);
			
			return PageResult.<MesProductDto>builder().data(pds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
		
	}
	//查询钢锭（钢锭查询页面展示）
	@Override
	public PageResult<MesProductDto> productIronList(searchProductParam param, PageQuery page) {
		
		BeanValidator.check(page);

		searchProductDto dto = new searchProductDto();
		
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		
		if(param.getProductHeatNumber()!=null) {
			dto.setProductHeatNumber(param.getProductHeatNumber());
		}
		if(param.getProductStatus()!=null) {
			dto.setProductStatus(param.getProductStatus());
		}
		
		dto.setProductMaterialsource("钢锭");
		
		int counts = mesProductCustomerMapper.getProductCountsBySomeListth(dto);
		if(counts > 0) {
			List<MesProductDto> pds = mesProductCustomerMapper.getProductBachListth(dto,page);
			
			return PageResult.<MesProductDto>builder().data(pds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
	}

	//查询可绑定的材料
	@Override
	public PageResult<MesProductDto> productBindList(searchProductParam param, PageQuery page) {
		BeanValidator.check(page);

		searchProductDto dto = new searchProductDto();
		
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		
		if(param.getProductHeatNumber()!=null) {
			dto.setProductHeatNumber(param.getProductHeatNumber());
		}
		
		if(StringUtils.isNotBlank(param.getProductMaterialsource())) {
			dto.setProductMaterialsource(param.getProductMaterialsource());
		}
		dto.setProductStatus(1);
		
		int counts = mesProductCustomerMapper.getProductCountsBySomeList(dto);
		if(counts > 0) {
			List<MesProductDto> pds = mesProductCustomerMapper.getProductBachList(dto,page);
			
			return PageResult.<MesProductDto>builder().data(pds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
	}

	//根据ID查询材料-材料绑定页面传递父级材料参数
	@Override
	public MesProduct productSelectOne(String id) {
		
		Integer newid = Integer.parseInt(id);
		MesProduct mp=mesProductMapper.selectByPrimaryKey(newid);
		if(mp!=null) {
			return mp;
		}
		return null;
	}
	
	// 查询未绑定的钢锭
	@Override
	public PageResult<MesProductDto> productqueryBind(PageQuery page) {
		
		searchProductDto dto = new searchProductDto();
		dto.setProductMaterialsource("钢锭");
		dto.setProductStatus(0);
		dto.setPidIsNull(null);
		
		int counts = mesProductCustomerMapper.getProductCountsBySomeList(dto);
		if(counts > 0) {
			List<MesProductDto> pds = mesProductCustomerMapper.getProductBachList(dto,page);
			
			return PageResult.<MesProductDto>builder().data(pds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
	}

	//绑定钢锭到材料上 
	@Override
	public boolean productBindOper(MesProduct product, String weight, String id) {
		
		Integer fuId = product.getId();
		String fuid = fuId.toString();
		String productId = product.getProductId();
		MesProduct product1 = productSelectOne(fuid);
		
		float nweight = Float.parseFloat(weight);
		float furealweight = product1.getProductBakweight();
		
		if(furealweight-nweight>=0) {
			
			ProductBindOperDto pdto1 = new ProductBindOperDto();
			pdto1.setId(id);
			pdto1.setFaId(fuId);
			pdto1.setTargetweight(weight);
			pdto1.setProductId(productId);
			
			ProductBindOperDto pdto2 = new ProductBindOperDto();
			pdto2.setFaId(fuId);
			pdto2.setTargetweight(weight);
			
			mesProductCustomerMapper.updateProductRealWeight(pdto1);
			
			mesProductCustomerMapper.updateProductBakWeight(pdto2);
			
			mesProductCustomerMapper.updateStatus(id);
			
			return true;
			
		}else {
			return false;
		}
	}

	//查询已绑定的钢锭
	@Override
	public PageResult<MesProductDto> productqueryBindRele(MesProduct product, PageQuery page) {
		
		Integer fuid = product.getId();
		String productId = product.getProductId();
		
		ProductBindOperDto pdto = new ProductBindOperDto();
		
		pdto.setNumberid(fuid);
		pdto.setProductId(productId);
		
		int counts = mesProductCustomerMapper.queryBindMuch(pdto);
		if(counts >0) {
			List<MesProductDto> mpds = mesProductCustomerMapper.queryBindRele(pdto,page);
			return PageResult.<MesProductDto>builder().data(mpds).total(counts).build();
		}
		return PageResult.<MesProductDto>builder().build();
		
	}

	//解除钢锭绑定
	@Override
	public void productBindRele(MesProduct mesProduct,String weight, String id) {
		
		Integer fuid = mesProduct.getId();
		
		ProductBindOperDto pdto = new ProductBindOperDto();
		pdto.setTargetweight(weight);
		pdto.setFaId(fuid);
		
		ProductBindOperDto pdto1 = new ProductBindOperDto();
		pdto1.setId(id);
		pdto1.setFaId(fuid);
		pdto1.setTargetweight(weight);
		
		mesProductCustomerMapper.LupdateStatus(id);
		
		mesProductCustomerMapper.LupdateProductBakWeight(pdto);
		
		mesProductCustomerMapper.LupdateProductRealWeight(pdto1);
	}

	//更新材料信息
	@Override
	public void updateProduct(MesProductVo mesProductVo) {
		BeanValidator.check(mesProductVo);
		MesProduct mp =  MesProduct.builder().id(mesProductVo.getId()).productImgid(mesProductVo.getProductImgid())
				.productMaterialname(mesProductVo.getProductMaterialname()).productMaterialsource(mesProductVo.getProductMaterialsource())
				.productTargetweight(mesProductVo.getProductTargetweight()).productRealweight(mesProductVo.getProductRealweight())
				.productLeftweight(mesProductVo.getProductLeftweight()).productIrontype(mesProductVo.getProductIrontype())
				.productIrontypeweight(mesProductVo.getProductIrontypeweight()).productHeatNumber(mesProductVo.getProductHeatNumber()).build();
		mesProductMapper.updateByPrimaryKeySelective(mp);
		
	}

}
