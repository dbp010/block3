<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if house??>
	<div id="house_table">
		<table class="house_table">
			<tr>
   				 <td>
   				 	<h1>
   				 		${house.name}
   				 	</h1>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Sitz
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if house.seat??>
						${house.seat.name} ( 
							<a href="location?lid=${house.seat.location.lid}">
       				 			${house.seat.location.name}
       				 		</a>
       				 		)
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Personen
   				 	</h2>
   				 </td> 
			</tr>
    			<#list members as member>
	    			<tr>
	       				 <td>
	       				 	<a href="person?cid=${member.person.cid}">
	       				 		${member.person.name}
	       				 	</a>
	       				 </td>
	    			</tr>
	    		</#list>
			<tr>
   				 <td>
   				 	<h2>
   				 		Besitz (Ort)
   				 	</h2>
   				 </td> 
			</tr>
			<#list belongings as belonging>
    			<tr>
       				 <td>
       				 	<a href="location?lid=${belonging.location.lid}">
       				 		${belonging.location.name}
       				 	</a>
       				 </td>
    			</tr>
	    	</#list>
		</table>
	</div>
<#else>

</#if>
		
<#-- END -->

<#include "common/html_end.ftl">