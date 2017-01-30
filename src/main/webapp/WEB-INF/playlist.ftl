<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<#if playlist??>
<div id="playlist">
	<h1>${playlist.name}</h1>
	
	<table	class	=	"playlist_episodes_table">	
		<#assign i = 0>
		<#list episodes as episode>
		<#assign i = i + 1 >
		<tr>
			<td>
				${i}
			</td>
			<td>
				<a href="episode?eid=${episode.eid}">S${episode.season.number}E${episode.number}</a>
			</td>
			<td>
				Play
			</td
		</tr>
		</#list>
		
	</table>
	
</div>
<#else>

</#if>

<#-- END -->

<#include "common/html_end.ftl">