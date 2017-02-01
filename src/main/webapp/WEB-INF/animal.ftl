<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if animal??>
	<div id="animal_table">
		<table class="animal_table">
			<tr>
   				 <td>
   				 	<h1>
   				 		${animal.name}
   				 	</h1>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Herkunftsort
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if animal.birthplace??>
						<a href="location?lid=${animal.birthplace.lid}">
							${animal.birthplace.name}
   				 		</a>
   				 	</#if>
   				 </td> 
			</tr>
		</table>
	</div>
	
	<#include "common/ratings.ftl">
	
<#else>

</#if>

<#-- END -->

<#include "common/html_end.ftl">
