<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>HOME</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
      <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="pen-title">
  <h1>HOME</h1>
 <span><a>${err}</a></span>
  
</div>
<div class="container">
  <div class="card"></div>
  <div class="card">
    <h1 class="title">Login</h1>
    <form action="home" method="post">
      <div class="input-container">
        <input type="text" id="label" name="luser" required="required" autocomplete="off"/>
        <label for="text">Username</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" id="label" name="lpass" required="required" autocomplete="off"/>
        <label for="password">Password</label>
        <div class="bar"></div>
      </div>
      <div class="button-container">
        <button><span>Go</span></button>
      </div>
    </form>
  </div>
  <div class="card alt">
    <div class="toggle"></div>
    <h1 class="title">Register
      <div class="close"></div>
    </h1>
    <form action="home" method="post">  
      <div class="input-container">
        <input type="text" id="label" name="ruser" required="required" autocomplete="off"/>
        <label for="text">Username</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" id="label" name="rpass" required="required" autocomplete="off"/>
        <label for="password">Password</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" id="label" name="rrpass" required="required" autocomplete="off"/>
        <label for="password">Repeat Password</label>       
		<div class="bar">
			<span style="display: table;margin-left: auto;margin-right: auto;">
				<img src="${d}" alt="Captcha" style="position: relative;margin-top: 30px;float: left;">
			</span>	
		</div>
      </div>
      <div class="button-container">
        <button><span>GO</span></button>
      </div>
    </form>
  </div>
</div>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script  src="js/index.js"></script>
</body>
</html>
