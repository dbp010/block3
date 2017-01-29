<table class="figures_table">
	
	<tr>
		 <th>Name</th>
		 <th>Typ</th> 
	</tr>
	
	<#list figures as figure>
	<tr>
	 	<td>
	 		<#include "create_figures_link.ftl">
	 	</td>
	 	<td>
			<#include "get_figure_type.ftl">
		</td> 
	</tr>
	</#list>
	
</table>