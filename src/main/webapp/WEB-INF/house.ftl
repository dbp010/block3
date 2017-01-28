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
		<#if house??>
			<div id="house_table">
				<table class="house_table">
	    			<tr>
	       				 <td>
	       				 	<h1>
	       				 		${house.name}
	       				 	</h1>
	       				 </td> 
	    			</tr>
	    			<tr>
	       				 <td>
	       				 	<h2>
	       				 		Sitz
	       				 	</h2>
	       				 </td> 
	    			</tr>
	    			<tr>
	       				 <td>
	       				 	<#if house.seat??>
								${house.seat.name} ( 
									<a href="location?lid=${house.seat.location.lid}">
		       				 			${house.seat.location.name}
		       				 		</a>
		       				 		)
	       				 	</#if>
	       				 </td> 
	    			</tr>
	    			<tr>
	       				 <td>
	       				 	<h2>
	       				 		Personen
	       				 	</h2>
	       				 </td> 
	    			</tr>
		    			<#list members as member>
			    			<tr>
			       				 <td>
			       				 	<a href="person?cid=${member.person.cid}">
			       				 		${member.person.name}
			       				 	</a>
			       				 </td>
			    			</tr>
			    		</#list>
	    			<tr>
	       				 <td>
	       				 	<h2>
	       				 		Besitz (Ort)
	       				 	</h2>
	       				 </td> 
	    			</tr>
	    			<#list belongings as belonging>
		    			<tr>
		       				 <td>
		       				 	<a href="location?lid=${belonging.location.lid}">
		       				 		${belonging.location.name}
		       				 	</a>
		       				 </td>
		    			</tr>
			    	</#list>
	  			</table>
			</div>
		<#else>
		
		</#if>
		</div>
	</div>
</body>
</html>
