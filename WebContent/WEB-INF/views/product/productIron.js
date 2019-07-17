$(function() {

	var productMap={};
	var keyword;// 关键字查询
	var pageSize;// 一页要展示的数据条数
	var productStatus;// 钢锭状态
	var url;// url
	var pageNo;// 当前页
	var productHeatNumber;

	var productInListTemplate = $("#productInListTemplate").html();

	Mustache.parse(productInListTemplate);
	loadProductList();// 将查询出来的数据进行分页展示

	// 刷新页面重新调用分页函数
	$("#research").click(function(e) {

		e.preventDefault();
		$("#productIronPage .pageNo").val(1);
		loadProductList();

	});

	function loadProductList(urlnew) {

		pageSize = $("#pageSize").val();
		pageNo = $("#productIronPage .pageNo").val() || 1;
		keyword = $("#keyword").val();
		productStatus = $("#productStatus").val();
		productHeatNumber = $("#productHeatNumber").val();

		if (urlnew != null) {
			url = urlnew;
		} else {
			url = "/product/productIron.json";
		}

		$.ajax({
			url : url,
			data : {
				pageSize : pageSize,
				pageNo : pageNo,
				keyword : keyword,
				productStatus : productStatus,
				productHeatNumber : productHeatNumber,
			},
			type : 'POST',
			success : function(result) {

				renderProductListAndPage(result, url);
			}
		});

	}

	function renderProductListAndPage(result, url) {

		if (result.ret) {

			pageSize = $("#pageSize").val();
			pageNo = $("#productIronPage .pageNo").val() || 1;
			keyword = $("#keyword").val();
			productStatus = $("#productStatus").val();
			productHeatNumber = $("#productHeatNumber").val();

			url = "/product/productIron.json";

			if (result.data.total > 0) {

				var rendered = Mustache
						.render(
								productInListTemplate,
								{
									"pruductInToList" : result.data.data,

									"showStatus" : function() {
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
				$('#productIronList').html(rendered);
			} else {
				$('#productIronList').html('');
			}
			bindProductClick();//更新操作
			var pageSize = $("#pageSize").val();
			var pageNo = $("#productIronPage .pageNo").val() || 1;
			// 渲染页码
			renderPage(url,// "/product/productListTo.json"
			result.data.total,// 总条数
			pageNo,// 1
			pageSize,// 一页有多少条数据
			result.data.total > 0 ? result.data.data.length : 0,// currentSize =
																// 10
			"productIronPage", // idElement
			loadProductList// callback
			);
		} else {
			showMessage("获取订单列表", result.msg, false);
		}
	}
	// 修改product
	// ////////////////////////////////////////////////////////////
	function bindProductClick() {
		$(".product-edit")
				.click(
		function(e) {
			// 阻止默认事件
			e.preventDefault();
			// 阻止事件传播
			e.stopPropagation();
			// 获取productid
			var productId = $(this).attr("data-id");
			// 弹出plan的修改弹窗
			$("#dialog-productUpdate-form").dialog({
				model : true,
				title : "更新材料",
				open : function(event, ui) {
					$(".ui-dialog").css(
							"width", "600px");
					$(
							".ui-dialog-titlebar-close",
							$(this).parent())
							.hide();
					// 将form表单中的数据清空，使用jquery转dom对象
					$("#productUpdateForm")[0]
							.reset();
					// 拿到map中以键值对，id-plan对象结构的对象,用来向form表单中传递数据
					var targetProduct = productMap[productId];
					// 如果取出这个对象
					if (targetProduct) {
						// ///////////////////////////////////////////////////////////////
						$("#input-id2")
								.val(
										targetProduct.id);
						$(
								"#input-productImgid2")
								.val(
										targetProduct.productImgid);
						$(
								"#input-productMaterialname2")
								.val(
										targetProduct.productMaterialname);
						$(
								"#input-productMaterialsource2")
								.val(
										targetProduct.productMaterialsource);
						$(
								"#input-productTargetweight2")
								.val(
										targetProduct.productTargetweight);
						$(
								"#input-productIrontypeweight2")
								.val(
										targetProduct.productIrontypeweight);
						$(
								"#input-productIrontype2")
								.val(
										targetProduct.productIrontype);
						$(
								"#input-productRemark2")
								.val(
										targetProduct.productRemark);
						$(
								"#input-productRealweight2")
								.val(
										targetProduct.productRealweight);
						$(
								"#input-productLeftweight2")
								.val(
										targetProduct.productLeftweight);
						$("#input-input-productHeatNumber2").val(targetProduct.productHeatNumber);
						// ///////////////////////////////////////////////////////////////
					}
				},
				buttons : {
					"更新" : function(e) {
						e.preventDefault();
						updateProduct(
								false,
								function(data) {
									$(
											"#dialog-productUpdate-form")
											.dialog(
													"close");
									$(
											"#productPage .pageNo")
											.val(
													1);
									loadProductList();
								},
								function(data) {
									showMessage(
											"更新材料",
											data.msg,
											false);
								})
					},
					"取消" : function(data) {
						$(
								"#dialog-productUpdate-form")
								.dialog("close");
					}
				}
			});
		});
	}

	// //////////////////////////////////////////////////////////

	// 修改product
	// successCallbak function(data) failCallbak function(data)
	function updateProduct(isCreate, successCallbak, failCallbak) {
$.ajax({
			url : isCreate ? "/product/updateProduct.json"
					: "/product/updateProduct.json",
			data : isCreate ? $("#orderForm").serializeArray() : $(
					"#productUpdateForm").serializeArray(),
			type : 'POST',
			success : function(result) {
				// 数据执行成功返回的消息
				if (result.ret) {
					loadProductList(); // 带参数回调
					// 带参数回调
					if (successCallbak) {
						successCallbak(result);
					}
				} else {
					// 执行失败后返回的内容
					if (failCallbak) {
						failCallbak(result);
					}
				}
			}
		});
	}
	// ////////////////////////////////////////////////////////////

});