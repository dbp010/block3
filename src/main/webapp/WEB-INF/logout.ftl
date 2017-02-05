<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<div	id	=	"logged_out">

	<#if logged_out_user??>
		Benutzer: ${logged_out_user.name } abgemeldet.
	<#else>
		Sie sind bereits abgemeldet.
	</#if>
	
</div>
<#-- END -->

<#include "common/html_end.ftl">