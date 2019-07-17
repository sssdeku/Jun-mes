<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="productBindListTemplate" type="x-tmpl-mustache">
{{#pruductBindList}}
<tr role="row" class="plan-name odd" data-id="{{id}}"><!--even -->
	<td>{{productId}}</td>
	<td><a href="#" data-id="{{pId}}">{{pId}}</a></td>
	<td>{{productMaterialname}}</td>
	<td>{{productMaterialsource}}</td>
	<td>{{productTargetweight}}</td>
	<td>{{productRealweight}}</td>
	<td>{{productLeftweight}}</td>
	<td>{{productBakweight}}</td>
	<td>{{productImgid}}</td>
	<td>{{productIrontype}}</td>
	<td>{{productIrontypeweight}}</td>
	<td>{{#bold}}{{showStatus}}{{/bold}}</td> 
	<td>{{productHeatNumber}}</td>
	<td>{{productRemark}}</td>
	
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
				 
			<a class="blue product-Bind" href="#" data-id="{{id}}">
				  <button class="Bind">点击绑定</button>
			</a>
		</div>
	</td>
</tr>
{{/pruductBindList}}
</script>			

