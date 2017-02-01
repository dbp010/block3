<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<#if entity??>
	<div	id	=	"entity_saved"	>
		<#include "common/snipplet/save/save_" + entity_type + ".ftl">
	</div>
</#if>
<#-- END -->

<#include "common/html_end.ftl">