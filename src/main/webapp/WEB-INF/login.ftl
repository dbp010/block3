<#include "common/html_begin.ftl">

<#-- START: BODY CONTENT GOES HERE -->
<#if user??>
	
	<div	id	=	"logged_in">
		Angemeldet als: ${user.name}
	</div>
	
<#else>

	<#if	error?? >
		<div	id	=	"login_error_message">
			<#if	error == 'credentials_incorrect'>
				Anmeldedaten falsch
			<#else>
				${error}
			</#if>
		</div>
	</#if>
	
	<div	id	=	"not_logged_in">
		<form
			id		=	"login_form"
			name	=	"login_form" 
			action	=	"login" 
			method	=	"post">
			
			<input 
					type	=	"hidden"
					name	=	"f" 
				/>
				
			<div	id	=	"login_form_name">
				Login:
				<input 
					type	=	"text"
					name	=	"l" 
				/>
			</div>
			
			<div	id	=	"login_form_password">
				Password:
				<input
					type	=	"password"
					name	=	"p"
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
	
	<div	id	=	"register">
		<a	href="register">Registrieren</a>
	</div>
	
</#if>
<#-- END -->

<#include "common/html_end.ftl">