<html>
<head><title>GoT Datenbank</title>
<style type="text/css">
* {
   margin:0;
   padding:0;
}

body{
   text-align:center;
   background: #efe4bf none repeat scroll 0 0;
}

#wrapper{
   width:960px;
   margin:0 auto;
   text-align:left;
   background-color: #fff;
   border-radius: 0 0 10px 10px;
   padding: 20px;
   box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
}

#site{
    background-color: #fff;
    padding: 20px 0px 0px 0px;
}
.centerBlock{
	margin:0 auto;
}
</style>

<body>
	<div id="wrapper">
	    <div id="logo">
			<img width="100%" src="images/header.jpg" class="centerBlock" />
		</div>
		<div id="site">
		
		<#--
		<p>
			Die Datenbank "${db2name}" ist ${db2exists}
		</p>
		-->
			<div id="figures">
				<h1>Figuren</h1>
				<table class="figures_table">
	    			<tr>
	       				 <th>Name</th>
	       				 <th>Typ</th> 
	    			</tr>
	    			
	    			<#list figures as figure>
	    			<tr>
	       			 	<td>
	       			 		<#-- START -->
	       			 		<#-- Build the link to person / animal site -->
	       			 		<#if figure.type == 'person'>
	       			 			<a href="person?cid=${figure.cid}">${figure.name}</a>
	       			 		<#elseif figure.type == 'animal'>
	       			 			<a href="animal?cid=${figure.cid}">${figure.name}</a>
	       			 		<#else>
	       			 			<#-- unknown entity type -->
	       			 			${figure.name}
	       			 		</#if>
	       			 		<#-- END -->
	       			 	</td>
	       			 	<td>
	    					<#if figure.type == 'person'>
	       			 			Person
	       			 		<#elseif figure.type == 'animal'>
	       			 			Tier
	       			 		<#else>
	       			 			Unbekannt
	       			 		</#if>
	    				</td> 
	    			</tr>
	    			</#list>
	    			
	  			</table>
			</div>
			
			<div id="houses">
				<h1>Häuser</h1>
				<table class="houses_table">
	    			<tr>
	       				 <th>Name</th> 
	    			</tr>
	    			
	    			<#list houses as house>
	    			<tr>
	    				<td>
	    					<a href="house?hid=${house.hid}">${house.name}</a>
	    				</td>
	    			</tr>
	    			</#list>
	    			
	  			</table>
			</div>
			
			<div id="seasons">
				<h1>Staffeln</h1>
				<table class="seasons_table">
	    			<tr>
	       				 <th>Nummer</th> 
	    			</tr>
	    			
	    			<#list seasons as season>
	    			<tr>
	    				<td>
	    					<a href="season?sid=${season.sid}">Staffel ${season.number}</a>
	    				</td>
	    			</tr>
	    			</#list>
	    			
	  			</table>
			</div>
			
			<div id="playlists">
				<h1>Playlisten</h1>
				<table class="playlists_table">
	    			<tr>
	       				 <th>Titel</th> 
	    			</tr>
	    			
	    			<#list playlists as playlist>
	    			<tr>
	    				<td>
	    					<a href="playlist?plid=${playlist.plid}">${playlist.name}</a>
	    				</td>
	    			</tr>
	    			</#list>
	    			
	  			</table>
			</div>
		</div>
	</div>
</body>
</html>
