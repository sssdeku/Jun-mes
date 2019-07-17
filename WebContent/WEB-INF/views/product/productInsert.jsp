<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>材料管理</title>

<%@ include file="/common/backend_common.jsp" %>
<script src="productInsert.js"></script>

</head>
<body class="no-skin" youdao="bind" style="background: white">
	<input id="gritter-light" checked="" type="checkbox"
		class="ace ace-switch ace-switch-5" />
	<div class="page-header">
		<h1>
			材料管理 <small><i class="ace-icon fa fa-angle-double-right"></i>
				新增材料 </small>
		</h1>
	</div>
	<div class="main-content-inner">
		<div class="col-sm-12">
			<div class="col-xs-12">
				<div class="table-header">
					材料生成选项&nbsp;&nbsp; <!-- <a class="green" href="#"> <i
						class="ace-icon fa fa-plus-circle orange bigger-130 order-add"></i>
					</a> -->
				</div>
				<div>
					<div id="dynamic-table_wrapper"
						class="dataTables_wrapper form-inline no-footer">
						
						<table id="dynamic-table"
							class="table table-striped table-bordered table-hover dataTable no-footer"
							role="grid" aria-describedby="dynamic-table_info"
							style="font-size: 14px">
							<thead>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">图号</font>
										<input class="col-xs-3" name="productImgid" id="productImgid">
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">材料名称</font>
										<input class="col-xs-3" name="productMaterialname" id="productMaterialname">
									</td>
								</tr>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">材料来源</font>
										
										<select id="productMaterialsource" name="productMaterialsource">
											<option value="钢材">钢材</option>
											<option value="废料">废料</option>
											<option value="外购件">外购件</option>
											<option value="外协件">外协件</option>
											<option value="钢锭">钢锭</option>
										</select>
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">工艺重量</font>
										<input class="col-xs-3" name="productTargetweight" id="productTargetweight">
									</td>
								</tr>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">投料重量</font>
										<input class="col-xs-3" name="productRealweight" id="productRealweight">
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">剩余重量</font>
										<input class="col-xs-3" name="productLeftweight" id="productLeftweight">
									</td>
								</tr>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">锭型</font>
										<input class="col-xs-3" name="productIrontypeweight" id="productIrontypeweight">
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">锭型类别</font>
										<input class="col-xs-3" name="productIrontype" id="productIrontype">
									</td>
								</tr>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">是否启用</font>
										
										<select id="productStatus" name="productStatus">
											<option value="0">未启用</option>
											<option value="1">已启用</option>
										</select>
										
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">备注</font>
										<input class="col-xs-3" name="productRemark" id="productRemark">
									</td>
								</tr>
								<tr role="row">
									<td class="col-xs-6">
										<font class="col-xs-3">批量生成个数</font>
										<input class="col-xs-3" name="count" id="count">
									</td>
									<td class="col-xs-6">
										<font class="col-xs-3">炉号</font>
										<select id="productHeatNumber"
										name="dynamic-table_length" aria-controls="dynamic-table"
										class="form-control input-sm">
											<option value="0">0</option>
										    <option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
									</select> 
										
									</td>
								</tr>
								<tr role="row">
									
									<td class="col-xs-6"> 
									<!-- <a href="/product/productBatchTo.page"> --><button id="addproduct">点击生成</button></a>
									</td>
								
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>