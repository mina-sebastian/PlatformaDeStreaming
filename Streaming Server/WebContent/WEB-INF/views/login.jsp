<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style.css" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<title>Register</title>
</head>
<body>
	<div class="login-form">
		<p class="header-login"> Register </p>
		${err}
		<form action="home" method="post">  
		<input type="text" class="input-form" placeholder="Username" name="ruser" required="required" autocomplete="off"> 
		<input type="password" class="input-form" placeholder="Password" name="rpass" required="required" autocomplete="off">
		<input type="password" class="input-form" placeholder="Repeat password" name="rrpass" required="required" autocomplete="off">
		<img src="${d}" class="imagine-mijloc">		
		<input type="text" class="input-form" placeholder="Captcha" name="captcha" required="required" autocomplete="off">
		<input type="submit" class="submit-login" value="Register" name="butonel">
		</form>
				<input type="submit" value="Login ->" 
    		onclick="window.location='/MinSrv/home?form=login';" class="submit-login"/>    
	</div>
</body>
</html>