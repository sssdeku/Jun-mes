<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="productListTemplate" type="x-tmpl-mustache">
{{#pruductBatchToList}}
<tr role="row" class="plan-name odd" data-id="{{id}}"><!--even -->
	<td><input name="checkbox" type="checkbox" class="batchStart-check"/></td>
	<td>{{productId}}</td>
	<td><a href="#" data-id="{{pId}}">{{pId}}</a></td>
	<td>{{productMaterialname}}</td>
	<td>{{productMaterialsource}}</td>
	<td>{{productTargetweight}}</td>
	<td>{{productRealweight}}</td>
	<td>{{productLeftweight}}</td>
	<td>{{productImgid}}</td>
	<td>{{productIrontype}}</td>
	<td>{{productIrontypeweight}}</td>
	<td>{{#bold}}{{showStatus}}{{/bold}}</td> 
	<td>{{productHeatNumber}}</td>
	<td>{{productRemark}}</td>
	
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
			 <a class="blue product-edit" href="#" data-id="{{id}}">
				  <i class="ace-icon fa fa-pencil bigger-100"></i>
			</a>
		</div>
	</td>
</tr>
{{/pruductBatchToList}}
</script>			


