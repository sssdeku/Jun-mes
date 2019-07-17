$(function(){
	
	var ids="";
	$(".batchStart-btn").click(function(){
		//拿到当前被选中的input-checkbox
		var checks=$(".batchStart-check:checked");
		if(checks.length!=null&&checks.length>0){
			//拿到被选中的订单号
			//mesorder-id
			$.each(checks,function(i,check){
				
//				console.log($(check).closest("tr").data("id")); h5
//				console.log($(check).closest("tr").attr("data-id"));
				var id=$(check).closest("tr").attr("data-id");
				ids+=id+"&";
			});
//			console.log(ids.substr(0,ids.length-1));
			//拼装ids
			ids=ids.substr(0,ids.length-1);
			//发送ajax请求
			$.ajax({
				url : "/product/productBatchStart.json",
				data : {//左面是数据名称-键，右面是值
					ids:ids
				},
				type : 'POST',
				success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
					loadProductList();
				}
			});
			ids="";//111&122&111&122
		}
	});

	$(".batchStart-th").click(function(){
		var checks=$(".batchStart-check");
		$.each(checks,function(i,input){
			//状态反选
//			console.log($(input).attr("checked"));调试测试
//			var checked=input.checked;
//			console.log(i+"--"+checked);
			//true-false  false-true  使用三目运算符
			input.checked=input.checked==true?false:true;
		});
	});
	
	
	
	
//////////////////////////////////////////////////////////////
	var productMap = {};
	var keyword;//关键字查询
	var pageSize;//一页要展示的数据条数
	var productMaterialsource;//材料来源
	var url;//url
	var pageNo;//当前页
	var productHeatNumber;
	
	var productListTemplate = $("#productListTemplate").html();
	
	Mustache.parse(productListTemplate);
	loadProductList();//将查询出来的数据进行分页展示
	
	//刷新页面重新调用分页函数
	$("#research").click(function(e){
		e.preventDefault();
		$("#productBatchPage .pageNo").val(1);
		loadProductList();
		
	});
	
	function loadProductList(urlnew){
		
		pageSize = $("#pageSize").val();
		pageNo = $("#productBatchPage .pageNo").val() || 1;
		keyword = $("#keyword").val();
		productMaterialsource = $("#productMaterialsource").val();
		productHeatNumber =$("#productHeatNumber").val();
		
		if(urlnew!=null){
			url = urlnew;
		}else{
			url = "/product/productListTo.json";
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
			pageNo = $("#productBatchPage .pageNo").val() || 1;
			keyword = $("#keyword").val();
			productMaterialsource = $("#productMaterialsource").val();
			productHeatNumber = $("#productHeatNumber").val();
			
			url = "/product/productListTo.json";
			
			if (result.data.total > 0) {
				
				var rendered = Mustache.render(
								productListTemplate,
								{
									"pruductBatchToList" : result.data.data,
									
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
				
				$.each(result.data.data,function(i,product){
					productMap[product.id] = product;
				});
				
				$('#productList').html(rendered);
			}else {
				$('#productList').html('');
			}
			bindProductClick();//更新操作
				var pageSize = $("#pageSize").val();
				var pageNo = $("#productBatchPage .pageNo").val() || 1;
				//渲染页码
				renderPage(
						url,// "/product/productListTo.json"
						result.data.total,//总条数
						pageNo,//1
						pageSize,//一页有多少条数据
						result.data.total > 0 ? result.data.data.length : 0,//currentSize = 10
						"productBatchPage", //idElement
						loadProductList//callback
						);
			} else {
				showMessage("获取订单列表", result.msg, false);
			}
		}
//修改product
//////////////////////////////////////////////////////////////
	function bindProductClick(){
		   $(".product-edit").click(function(e) {
			//阻止默认事件
            e.preventDefault();
			//阻止事件传播
            e.stopPropagation();
			//获取productid
            var productId = $(this).attr("data-id");
			//弹出plan的修改弹窗 
            $("#dialog-productUpdate-form").dialog({
                model: true,
                title: "更新材料",
                open: function(event, ui) {
             	    $(".ui-dialog").css("width","600px");
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                  	//将form表单中的数据清空，使用jquery转dom对象
                    $("#productUpdateForm")[0].reset();
                  	//拿到map中以键值对，id-plan对象结构的对象,用来向form表单中传递数据
                    var targetProduct = productMap[productId];
                  	//如果取出这个对象
                    if (targetProduct) {
						/////////////////////////////////////////////////////////////////
						$("#input-id2").val(targetProduct.id);
						$("#input-productImgid2").val(targetProduct.productImgid);
						$("#input-productMaterialname2").val(targetProduct.productMaterialname);
						$("#input-productMaterialsource2").val(targetProduct.productMaterialsource);
						$("#input-productTargetweight2").val(targetProduct.productTargetweight);
						$("#input-productIrontypeweight2").val(targetProduct.productIrontypeweight);
						$("#input-productIrontype2").val(targetProduct.productIrontype);
						$("#input-productRemark2").val(targetProduct.productRemark);
						$("#input-productRealweight2").val(targetProduct.productRealweight);
						$("#input-productLeftweight2").val(targetProduct.productLeftweight);
						/////////////////////////////////////////////////////////////////
                    }
                },
                buttons : {
                    "更新": function(e) {
                        e.preventDefault();
                        updateProduct(false, function (data) {
                            $("#dialog-productUpdate-form").dialog("close");
            				$("#productPage .pageNo").val(1);
            				loadProductList();
                        }, function (data) {
                            showMessage("更新材料", data.msg, false);
                        })
                    },
                    "取消": function (data) {
                        $("#dialog-productUpdate-form").dialog("close");
                    }
                }
            });
        });
	   }  
	
	////////////////////////////////////////////////////////////
	
	//修改product
	//successCallbak function(data)  failCallbak function(data)
	function updateProduct(isCreate, successCallbak, failCallbak) {
		$.ajax({
			url : isCreate ? "/product/updateProduct.json"
					: "/product/updateProduct.json",
			data : isCreate ? $("#orderForm").serializeArray() : $(
					"#productUpdateForm").serializeArray(),
			type : 'POST',
			success : function(result) {
				//数据执行成功返回的消息
				if (result.ret) {
					loadProductList(); // 带参数回调
					//带参数回调
					if (successCallbak) {
						successCallbak(result);
					}
				} else {
					//执行失败后返回的内容
					if (failCallbak) {
						failCallbak(result);
					}
				}
			}
		});
	}
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
});