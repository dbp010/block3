<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if season??>
	<div id="season_table">
		<h1>
			Staffel ${season.number}
   		</h1>
   		<h2>
   			Episoden
   		</h2>
		<table class="season_table">
			<tr>
   				 <th>
   				 	Titel
   				 </th>
   				 <th>
   				 	Nummer
   				 </th>
   				 <th>
   				 	Ausstrahlung
   				 </th> 
			</tr>
			<#list episodes as episode>
    			<tr>
       				 <td>
       				 	<a href="episode?eid=${episode.eid}">
       				 		${episode.title}
       				 	</a>
       				 </td>
       				 <td>
       				 	${episode.number}
       				 </td>
       				 <td>
       				 	${episode.releasedate?string["dd.MM.yyyy"]}
       				 </td>
    			</tr>
	    	</#list>
		</table>
		
		<#include "common/ratings.ftl">

	</div>
<#else>

</#if>
		
<#-- END -->

<#include "common/html_end.ftl">