<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if person??>
	<div id="person_table">
		<table class="person_table">
			<tr>
   				 <td>
   				 	<h1>
   				 		${person.name}
   				 	</h1>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Titel
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if person.title??>
   				 		${person.title}
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Biografie
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if person.biografie??>
   				 		${person.biografie}
   				 	</#if>
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
   				 	<#if person.birthplace??>
   				 		${person.birthplace.name}
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Häuser
   				 	</h2>
   				 </td> 
			</tr>
			<#list members as member>
    			<tr>
       				 <td>
       				 	<a href="house?hid=${member.house.hid}">
       				 		${member.house.name}
       				 	</a>
       				 	 
       				 	<a href="episode?eid=${member.episode_from.eid}">
       				 		Episode ${member.episode_from.number}
       				 	</a>
       				 	-
       				 	<a href="episode?eid=${member.episode_to.eid}">
       				 		Episode ${member.episode_to.number}
       				 	</a>
       				 </td>
    			</tr>
    		</#list>
			<tr>
   				 <td>
   				 	<h2>
   				 		Beziehungen
   				 	</h2>
   				 </td> 
			</tr>
			<#list relationships as relationship>
    			<tr>
       				 <td>
       				 	<a href="person?cid=${relationship.targetp.cid}">
       				 		${relationship.targetp.name}
       				 	</a>
       				 	${relationship.rel_type}
       				 </td>
    			</tr>
    		</#list>
    		<tr>
   				 <td>
   				 	<h2>
   				 		Tiere
   				 	</h2>
   				 </td> 
			</tr>
			<#list animals as animal>
    			<tr>
       				 <td>
       				 	<a href="animal?cid=${animal.cid}">
       				 		${animal.name}
       				 	</a>
       				 </td> 
    			</tr>
    		</#list>
		</table>
	</div>
	
	<#include "common/ratings.ftl">
	
<#else>

</#if>
		
<#-- END -->

<#include "common/html_end.ftl">