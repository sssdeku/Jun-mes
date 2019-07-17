$(function(){
	function bindOrderClick(){
		$(".Bind").click(function(e){
			e.preventDefault();
			var id = $(this).closest("tr").attr("data-id");
			
			$.ajax({
				url:"/product/productBind.json",
				data:{
					id:id,
				},
				type:'POST',
				success : function(result){
					window.location.href="/product/productBind.page";
				}
			})
		});
	}
	
	var keyword;//关键字查询
	var pageSize;//一页要展示的数据条数
	var productMaterialsource;//材料来源
	var url;//url
	var pageNo;//当前页
	var productHeatNumber;
	
	var productBindListTemplate = $("#productBindListTemplate").html();
	
	Mustache.parse(productBindListTemplate);
	loadProductList();//将查询出来的数据进行分页展示
	
	//刷新页面重新调用分页函数
	$("#research").click(function(e){
		
		e.preventDefault();
		$("#productBindPage .pageNo").val(1);
		loadProductList();
		
	});
	
	function loadProductList(urlnew){
		
		pageSize = $("#pageSize").val();
		pageNo = $("#productBindPage .pageNo").val() || 1;
		keyword = $("#keyword").val();
		productMaterialsource = $("#productMaterialsource").val();
		productHeatNumber =$("#productHeatNumber").val();
		
		if(urlnew!=null){
			url = urlnew;
		}else{
			url = "/product/productBindList.json";
		}
		
		$.ajax({
			url : url,
			data : {
				pageSize : pageSize,
				pageNo : pageNo,
				keyword : keyword,
				productMaterialsource :productMaterialsource,
				productHeatNumber : productHeatNumber,
			},
			type : 'POST',
			success : function(result){
				
			renderProductListAndPage(result, url);
		}
		});
		
	}
	
	function renderProductListAndPage(result, url){
		
		if (result.ret) {
			
			pageSize = $("#pageSize").val();
			pageNo = $("#productBindPage .pageNo").val() || 1;
			keyword = $("#keyword").val();
			productMaterialsource = $("#productMaterialsource").val();
			productHeatNumber = $("#productHeatNumber").val();
			
			url = "/product/productBindList.json";
			
			if (result.data.total > 0) {
				
				var rendered = Mustache.render(
									productBindListTemplate,
								{
									"pruductBindList" : result.data.data,
									
									"showStatus":function(){
										return this.productStatus == 1 ? '有效'
												: (this.productStatus == 0 ? '无效'
														: '删除');
									},
									
									"bold" : function() {
										return function(text, render) {
											var status = render(text);
											if (status == '有效') {
												return "<span class='label label-sm label-success'>有效</span>";
											} else if (status == '无效') {
												return "<span class='label label-sm label-warning'>无效</span>";
											} else {
												return "<span class='label'>删除</span>";
											}
										}
									}
								});
				
				$('#productBindList').html(rendered);
			}else {
				$('#productBindList').html('');
			}
			 bindOrderClick();//更新操作
				var pageSize = $("#pageSize").val();
				var pageNo = $("#productBindPage .pageNo").val() || 1;
				//渲染页码
				renderPage(
						url,// "/product/productListTo.json"
						result.data.total,//总条数
						pageNo,//1
						pageSize,//一页有多少条数据
						result.data.total > 0 ? result.data.data.length : 0,//currentSize = 10
						"productBindPage", //idElement
						loadProductList//callback
						);
			} else {
				showMessage("获取订单列表", result.msg, false);
			}
		}
	
	
});