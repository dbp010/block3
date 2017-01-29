<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if episode??>
	<div id="episode_table">
		<table class="episode_table">
			<tr>
   				 <td>
   				 	<h1>
   				 		Episode (${episode.title} - ${episode.number})
   				 	</h1>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Staffel
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<a href="season?sid=${episode.season.sid}">
   				 		Staffel ${episode.season.number}
   				 	</a>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Handlung
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if episode.summary??>
   				 		${episode.summary}
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Figuren
   				 	</h2>
   				 </td> 
			</tr>
			<#list figures as figure>
				<tr>
				 	<td>
				 		<#include "common/snipplet/create_figures_link.ftl">
				 	</td> 
				</tr>
			</#list>
			<tr>
   				 <td>
   				 	<h2>
   				 		Orte
   				 	</h2>
   				 </td> 
			</tr>
			<#list locations as location>
    			<tr>
       				 <td>
       				 	<a href="location?lid=${location.lid}">
       				 		${location.name}
       				 	</a>
       				 </td>
    			</tr>
	    	</#list>
		</table>
		
		<#assign rating_type = "house">
		<#include "common/ratings.ftl">

	</div>
<#else>

</#if>
		
<#-- END -->

<#include "common/html_end.ftl">