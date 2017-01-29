<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<#if user??>
	<div	id	=	"logged_in">
		Angemeldet als: ${user.name}
	</div>
<#else>
	<div	id	=	"not_logged_in">
		<form
			id		=	"login_form"
			name	=	"login_form" 
			action	=	"login" 
			method	=	"post">
			
			<div	id	=	"login_form_name">
				Name:
				<input 
					type	=	"text"
					name	=	"n" 
				/>
			</div>
			<div	id	=	"login_form_password">
				Password:
				<input
					type	=	"password"
					value	=	"p"
				/>
			</div>
			<div	id	=	"login_form_submit">
				<input
					type	=	"submit"
					value	=	"Anmelden"
				/>
			</div>
  		</form>
	</div>
</#if>
<#-- END -->

<#include "common/html_end.ftl">