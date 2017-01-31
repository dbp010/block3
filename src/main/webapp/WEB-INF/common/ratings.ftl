<div	id	=	"ratings">
	<div	id	"user_rating">
		<form
			name	=	"user_rating_form" 
			action	=	"save" 
			method	=	"post">
			
			<input 
				type	=	"hidden"
				name	=	"entity_type"
				value	=	"rating"
			/>
			
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
			
  		</form>
	</div>
	
	<div	id	=	"users_rating">
		<h3>
			Alle Bewertungen
		</h3>
		
		<div	id	=	"avg_rating">
			Bewertung im Durchschnitt: ${avg_rating}
		</div>
		
		<#list ratings as rating>
			
			<div	id	=	"rating_user">
				Benutzer: ${rating.user.name}
			</div>
			
			<div	id	=	"user_rating">
				Bewertung: ${rating.rating}
			</div>
			
			<div	id	=	"user_rating_text">
				${rating.text}
			</div>
			
		</#list>
	</div>
	
</div>