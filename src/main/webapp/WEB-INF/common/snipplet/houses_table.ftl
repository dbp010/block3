<table class="houses_table">

	<tr>
		 <th>Name</th> 
	</tr>
	
	<#list houses as house>
	<tr>
		<td>
			<a href="house?hid=${house.hid}">${house.name}</a>
		</td>
	</tr>
	</#list>
	
</table>