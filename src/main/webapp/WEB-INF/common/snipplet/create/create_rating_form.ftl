<input 
	type	=	"hidden"
	name	=	"rating_type"
	value	=	"${rating_type}"
/>

<input 
	type	=	"hidden"
	name	=	"usid"
	value	=	"${user.usid}"
/>

Deine Bewertung:

<select		name	=	"selected_rating">
	<#if user_rating??>
		<option		value		=	"${user_rating.rating}"	>${user_rating.rating}</option>
		<option 	disabled	=	"true"					>---</option>    			
	</#if>
	
	<option		value	=	"5"	>5</option>
	<option 	value	=	"4"	>4</option>
	<option 	value	=	"3"	>3</option>
	<option 	value	=	"2"	>2</option>
	<option 	value	=	"1"	>1</option>
	<option 	value	=	"1"	>0</option>
	
</select>

<textarea	name	=	"text"><#if user_rating??>${user_rating.text}</#if></textarea>

<input
	type	=	"submit"
	<#if user_rating??>
		value	=	"Ändern"
	<#else>
		value	=	"Bewerten"
	</#if>
/>