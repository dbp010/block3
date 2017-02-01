<div	id	=	"ratings">
	<div	id	"user_rating_form">
		
		<#assign	entity_type	=	'rating'>
		<#include "common/create_entity_form.ftl">
		
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