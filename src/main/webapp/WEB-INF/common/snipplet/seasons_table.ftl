<table class="seasons_table">
	
	<tr>
		 <th>
		 	Nummer
		 </th> 
	</tr>
	
	<#list seasons as season>
	<tr>
		<td>
			<a href="season?sid=${season.sid}">Staffel ${season.number}</a>
		</td>
	</tr>
	</#list>
	
</table>