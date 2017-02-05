Bewertung für  
<#if	entity.type 	== 	'character'>
Figur: ${entity.ratingEntity.character.name}
<#elseif entity.type	==	'episode'>
Episode: ${entity.ratingEntity.title}
<#elseif entity.type	==	'house'>
Haus: ${entity.ratingEntity.name}
<#elseif entity.type	==	'season'>
Staffel: ${entity.ratingEntity.number}
</#if>
erstellt.