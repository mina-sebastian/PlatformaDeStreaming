<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style.css" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js"></script>
	<title>By Sebi</title>
	<script src="js/err.js"></script>
</head>
<body style="overflow: hidden">
<div class="fof">
<canvas></canvas>
	<div class="login-form">
		<p class="header-login"> Login </p>
		${err}
		<form action="home" method="post">  
		<input type="text" class="input-form" placeholder="Username" name="luser" required="required" autocomplete="off"> 
		<input type="password" class="input-form" placeholder="Password" name="lpass" required="required" autocomplete="off">
		<input type="submit" class="submit-login" value="Login">
		</form>
				<input type="submit" value="Register ->" 
    		onclick="window.location='/MinSrv/home?form=register';" class="submit-login"/>    
	</div>
</div>
</body>
</html>