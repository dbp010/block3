<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<#if location??>
	<div id="location_table">
		<table class="location_table">
			<tr>
   				 <td>
   				 	<h1>
   				 		${location.name}
   				 	</h1>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		aktuell in Besitz von
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if belonging??>
						<a href="house?hid=${belonging.house.hid}">
   				 			${belonging.house.name}
   				 		</a>
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Burg
   				 	</h2>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<#if castle??>
						${castle.name}
   				 	</#if>
   				 </td> 
			</tr>
			<tr>
   				 <td>
   				 	<h2>
   				 		Personen (Herkunftsort)
   				 	</h2>
   				 </td> 
			</tr>
			<#list persons as person>
				<tr>
					<td>
						<a href="person?cid=${person.cid}">${person.name}</a>
					</td>
				</tr>
			</#list>
			<tr>
   				 <td>
   				 	<h2>
   				 		Episoden (Handlungsort)
   				 	</h2>
   				 </td> 
			</tr>
			<#list episodes as episode>
				<tr>
					<td>
						<a href="episode?eid=${episode.eid}">
							S${episode.season.number}E${episode.number}
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