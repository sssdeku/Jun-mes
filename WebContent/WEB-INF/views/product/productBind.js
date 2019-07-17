$(function(){

	var url;
	loadProdctList();
	var product;
	
	var pageSize;
	var pageNo;
	
	
	var productBindTemplate = $("#productBindTemplate").html();
	var productBoundTemplate = $("#productBoundTemplate").html();
	
	Mustache.parse(productBindTemplate);
	Mustache.parse(productBoundTemplate);
	
//分页展示未绑定和已绑定的材料
//////////////////////////////////////////////////////////
	function loadProdctList(){
		queryBind();// 查询并展示未绑定的材料
		queryBindRele();// 查询并显示已绑定的材料
		
	}
	
//更新页面中product中的数据
///////////////////////////////////////////////////////////////
	function getProduct(){
		
		$.ajax({
			url : "/product/productBind2.json",
			data : {
				
			},
			type : 'POST',
			success : function(result){
				product=result.data;
				window.location.href="/product/productBind.page";
			}
		})
		
	}
		

		
//分页函数-展示未绑定钢锭列表
////////////////////////////////////////////////////////////////////	
	function queryBind(){
		pageSize = $("#pageSize").val() || 5;
		pageNo = $("#productBindPage .pageNo").val() || 1;
		
		$.ajax({
			url:"/product/productqueryBind.json",
			data:{
				pageSize : pageSize,
				pageNo : pageNo
			},
			type:'POST',
			success : function(result,url){
				renderProductListAndPage(result,url);
			}
		})
	}
	
	function renderProductListAndPage(result,url){
		if(result.ret){
			if (result.data.total > 0) {
				var rendered = Mustache.render(
								productBindTemplate,
								{
									"productList" : result.data.data,
								});
				
				$('#productList').html(rendered);
			}else {
				$('#productList').html('');
			}
			Bind();
			var pageSize =5;
			var pageNo = $("#productBindPage .pageNo").val() || 1;
			//渲染页码
			renderPage(
					url,// "/product/productListTo.json"
					result.data.total,//总条数
					pageNo,//1
					pageSize,//一页有多少条数据
					result.data.total > 0 ? result.data.data.length : 0,//currentSize = 10
					"productBindPage", //idElement
					loadProdctList//callback
					);
		} else {
			showMessage("获取订单列表", result.msg, false);
		}
	}
//分页函数-展示已绑定材料列表
////////////////////////////////////////////////////////////////////////////
	function queryBindRele(){
		
		pageSize = $("#pageSize").val() || 5;
		pageNo = $("#productBoundPage .pageNo").val() || 1;
		
		$.ajax({
			url:"/product/productqueryBindRele.json",
			data:{
				pageSize : pageSize,
				pageNo : pageNo
			},
			type:'POST',
			success : function(result,url){
				renderProductBoundListAndPage(result,url);
			}
		})
		
	}
	
	function renderProductBoundListAndPage(result,url){
		
		if(result.ret){
			if (result.data.total > 0) {
				var rendered = Mustache.render(
							productBoundTemplate,
								{
									"productBoundList" : result.data.data,
								});
				
				$('#productBoundList').html(rendered);
			}else {
				$('#productBoundList').html('');
			}
			BindRele();
			var pageSize =5;
			var pageNo = $("#productBoundPage .pageNo").val() || 1;
			//渲染页码
			renderPages(
					url,// "/product/productListTo.json"
					result.data.total,//总条数
					pageNo,//1
					pageSize,//一页有多少条数据
					result.data.total > 0 ? result.data.data.length : 0,//currentSize = 10
					"productBoundPage", //idElement
					loadProdctList//callback
					);
		} else {
			showMessage("获取订单列表", result.msg, false);
		}
		
		
	}
	
	
	
//绑定操作
////////////////////////////////////////////////////////////////////////////
	function Bind(){
	$(".product-bind").click(
			function(){
				
		var weight = $(this).attr("data-weight");
		var id = $(this).attr("data-id");
		
		$.ajax({
			url:"/product/productBindOper.json",
			data:{
				weight :weight,
				id : id,
			},
			type:'POST',
			
			success : function(result){
				queryBind();
				queryBindRele();
				getProduct();
				showMes(result);
				
				}
			})
		
		});
	
	}
/////////////////////////////////////////
	function showMes(result){
		
		if(result.ret){
			
		}else{
			alert("理论剩余重量不够绑定该材料！！！");
		}
	}
//解除绑定
///////////////////////////////////////////////////////////////////////////
	function BindRele(){
		$(".bound-edit").click(
				function(){
			var id = $(this).attr("data-id");
			var targetweight = $(this).attr("data-weight");
			
			$.ajax({
				url : "/product/productBindRele.json",
				
				data : {
					id : id,
					weight:targetweight
					
				},
				
				type : 'POST',
				success : function(result){
					queryBindRele();
					queryBind();
					getProduct();
				}
			});
		});
		
	}
});