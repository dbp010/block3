<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<div	id	=	"logged_out">

	<#if user??>
		Benutzer: ${user.name } abgemeldet.
	<#else>
		Sie sind bereits abgemeldet.
	</#if>
	
</div>
<#-- END -->

<#include "common/html_end.ftl">