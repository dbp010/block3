<#-- Build the link to person / animal site -->
<#if figure.type == 'person'>
	<a href="person?cid=${figure.character.cid}">${figure.character.name}</a>
<#elseif figure.type == 'animal'>
	<a href="animal?cid=${figure.character.cid}">${figure.character.name}</a>
<#else>
	<#-- unknown entity type -->
	${figure.name}
</#if>