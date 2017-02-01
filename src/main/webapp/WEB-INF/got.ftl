<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->

<div id="figures">
	<h1>Figuren</h1>
	<#include "common/snipplet/figures_table.ftl">
</div>

<#assign entities_type = "figures">
<#include "common/search_entities.ftl">


<div id="houses">
	<h1>Häuser</h1>
	<#include "common/snipplet/houses_table.ftl">
</div>

<#assign entities_type = "houses">
<#include "common/search_entities.ftl">

<div id="seasons">
	<h1>Staffeln</h1>
	<#include "common/snipplet/seasons_table.ftl">
</div>

<#assign entities_type = "seasons">
<#include "common/search_entities.ftl">

<div id="playlists">
	<h1>Playlisten</h1>
	
	<table class="playlists_table">
		<tr>
			 <th>Titel</th> 
		</tr>
		
		<#list playlists as playlist>
		<tr>
			<td>
				<a href="playlist?plid=${playlist.plid}">${playlist.name}</a>
			</td>
		</tr>
		</#list>
		
	</table>
	
	<#assign	entity_type	=	'playlist'>
	<#include "common/create_entity_form.ftl">
	
</div>

<#-- END -->

<#include "common/html_end.ftl">