<form
	id		=	"create_${entity_type}_form"
	name	=	"create_${entity_type}_form" 
	action	=	"save"
	method	=	"post">
	
	<input 
		type	=	"hidden"
		name	=	"entity_type"
		value	=	"${entity_type}"
	/>
	
	<#include	"snipplet/create/create_" + entity_type + "_form.ftl">

</form>