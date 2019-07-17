package com.xijun.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xijun.common.SameUrlData;
import com.xijun.beans.PageQuery;
import com.xijun.beans.PageResult;

import com.xijun.common.JsonData;
import com.xijun.dto.MesProductDto;
import com.xijun.model.MesProduct;
import com.xijun.param.MesProductVo;
import com.xijun.param.searchProductParam;
import com.xijun.service.ProductService;

@SessionAttributes
@Controller
@RequestMapping("/product")
public class ProductController {

	@Resource
	private ProductService productService;
	
	
	private static final String PATH="product/";
	//跳转到材料管理-增加页面
	@RequestMapping("/productInsert.page")
	public String productinsert() {
		return PATH+"productInsert";
	}
	//跳转到批量入库页面
	@RequestMapping("/productBatchTo.page")
	public String productBatchTo(){
		return PATH+"productBatchTo";
	}
	//跳转-到库查询页面
	@RequestMapping("/productIn.page")
	public String productIn() {
		return  PATH+"productIn";
	}
	
	//跳转-钢锭查询页面
	@RequestMapping("/productIron.page")
	public String productIronPage() {
		return PATH+"productIron";
	}
	//跳转-材料绑定页面
	@RequestMapping("/productBindList.page")
	public String productBindList() {
		return PATH+"productBindList";
	}
	//跳转-材料具体的绑定页面
	@RequestMapping("/productBind.page")
	public String productBinds() {
		return PATH+"productBind";
	}
	//跳转-材料绑定二界面
	
	
	
	
	
	//更新材料
	@ResponseBody
	@RequestMapping("/updateProduct.json")
	public JsonData updateProduct(MesProductVo mesProductVo) {
		productService.updateProduct(mesProductVo);
		return JsonData.success();
	}
	
	//材料批量增加
	@SameUrlData//防止重复提交
	@RequestMapping("/productInsert.json")
	public void productInsert(MesProductVo mesProductVo) {
		
		productService.ProductBatchInsert(mesProductVo);
		
	}
	//批量入库页面数据查询并展示
	@ResponseBody
	@RequestMapping("/productListTo.json")
	public JsonData productListTo(searchProductParam param,PageQuery page) {
		
		PageResult<MesProductDto> ps = productService.SelectAllProductBatchTo(param,page);
		return JsonData.success(ps);
	}
	//材料批量到库
	@ResponseBody
	@RequestMapping("/productBatchStart.json")
	public JsonData productBatchStart(String ids) {
		
		productService.productBatchStart(ids);
		return null;
	}
	
	//查询已倒库的材料数据
	@ResponseBody
	@RequestMapping("/productInList.json")
	public JsonData productInList(searchProductParam param,PageQuery page) {
		
		PageResult<MesProductDto> mpds = productService.productInList(param,page);
		return JsonData.success(mpds);
	}
	//查询钢锭数据
	@ResponseBody
	@RequestMapping("/productIron.json")
	public JsonData productIronList(searchProductParam param,PageQuery page) {
		PageResult<MesProductDto> mpds = productService.productIronList(param,page);
		return JsonData.success(mpds);
	}
	//查询可绑定的材料数据
	@ResponseBody
	@RequestMapping("/productBindList.json")
	public JsonData productBindListOne(searchProductParam param,PageQuery page) {
		
		PageResult<MesProductDto> mpds = productService.productBindList(param,page);
		return JsonData.success(mpds);
	}
	
	MesProduct product;
	
	//跳转-材料绑定二界面
	@ResponseBody
	@RequestMapping("/productBind.json")
	public JsonData productBind(String id,HttpSession session) {
		product = productService.productSelectOne(id);
		session.setAttribute("product", product);
		return JsonData.success(product);
	}
	
	
	
	//更新页面中显示的product对象
	@ResponseBody
	@RequestMapping("/productBind2.json")
	public void productBind2(HttpSession session) {
		Integer iid = product.getId();
		String id = iid.toString();
		product = productService.productSelectOne(id);
		session.setAttribute("product", product);
	
	}
	
	//返回当前页面绑定原材料的数据
	@ResponseBody
	@RequestMapping("/productBindsss.json")
	public JsonData productBindsss() {
		return JsonData.success(product);
	}
	
	
	//查询可绑定的材料
	@ResponseBody
	@RequestMapping("/productqueryBind.json")
	public JsonData productqueryBind(PageQuery page) {
		PageResult<MesProductDto> mpds = productService.productqueryBind(page);
		return JsonData.success(mpds);
	}
	
	//绑定操作
	@ResponseBody
	@RequestMapping("/productBindOper.json")
	public JsonData productBindOper(String weight,String id) {
		boolean b= productService.productBindOper(product,weight,id);
		JsonData js = new JsonData(b);
		return js;
		
	}
	//查询已绑定材料
	@ResponseBody
	@RequestMapping("/productqueryBindRele.json")
	public JsonData productqueryBindRele(PageQuery page) {
		PageResult<MesProductDto> mpds=productService.productqueryBindRele(product,page);
		return JsonData.success(mpds);
	}
	
	//解除绑定
	@ResponseBody
	@RequestMapping("/productBindRele.json")
	public JsonData productBindRele(String weight,String id) {
		
		productService.productBindRele(product,weight,id);
		
		return JsonData.success();
	}
}
