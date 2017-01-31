<div	id	=	"menu">
	<div	id	=	"menu_item">
		<h2>
			<a href="got">
				Home
			</a>
		</h2>
	</div>
	<div	id	=	"menu_item">
		<h2>
			<a href="figures">
				Figuren
			</a>
		</h2>
	</div>
	<div	id	=	"menu_item">
		<h2>
			<a href="houses">
				Häuser
			</a>
		</h2>
	</div>
	<div	id	=	"menu_item">
		<h2>
			<a href="seasons">
				Staffeln
			</a>
		</h2>
	</div>
	<div	id	=	"menu_item">
		<h2>
			Benutzer:
			<#if user??>
				${user.name}
			<#else>
				Gast
			</#if>
		</h2>
	</div>
	<div	id	=	"menu_item">
		<h2>
			<#if user??>
				<a href="logout">
					Logout
				</a>
			<#else>
				<a href="login">
					Login
				</a>
			</#if>
		</h2>
	</div>
</div>