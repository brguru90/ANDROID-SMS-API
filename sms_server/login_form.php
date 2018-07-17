<?php
header('Cache-Control: max-age=900');
session_start();
if (isset($_COOKIE['user']))
{
	$_SESSION['user']=$_COOKIE['user'];
}
if(isset($_SESSION['user']))
{
	if($_SESSION['user']!="admin")
		header('Location: user_area.php');
	else
		header('Location: admin_area.php');
}
?>
<!DOCTYPE html>
<html>
<meta http-equiv="refresh" content="3600">
<head>
<title id="title">HOME</title>
<link href="fonts/gurufonts.css" type="text/css" rel="stylesheet" media="all">
<script src="api/jquery.js"></script>
<link href="css/bo
otstrap.css" type="text/css" rel="stylesheet" media="all">
<link rel="icon" 
  type="image/png" 
  href="images/img2.jpg">
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>


<!--//end-smoth-scrolling-->
<style>

@media only screen and (max-width: 1000px) {
	body{width:1200px}
}
</style>
</head>
<body>
		<style>
	html,body{
		background-color:pink;
		background: transparent url("images/aa.jpg") no-repeat;
		background-size: cover;		
}
	.login-form2 {
    margin: 0px;
    background: #FFF none repeat scroll 0% 0%;
    padding: 0px;
	width:80%;
	height:80%;
    position: relative;
	top:50px;
    left: 10%;
	padding:0px 0px 0px 0px;
}
input[type="submit"] {
    margin: 0.5em 2.5em 0px;
    padding: 8px 0px;
    font-size: 1em;
}
.login-form2 input[type="submit"] {
    background: #E64608 none repeat scroll 0% 0%;
    width: 51%;
    font-size: 1.2em;
    margin: 2em 4.5em 0px;
    color: #FFF;
    padding: 12px 0px;
    border: 1px solid #E64608;
    transition: all 0.5s ease 0s;
    outline: medium none;
}

.login-form2 form input[type="text"], .login-form2 form input[type="email"] {
    background: transparent url("../images/msg.png") no-repeat scroll 15px 13px;
        background-color: transparent;
        background-image: url("../images/msg.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 13px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    border: 1px solid #C2C2C2;
}

.login-form2 form input[type="password"] {
    background: transparent url("../images/key.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("../images/key.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 14px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
.login-form2 form {
    margin-bottom: 24px;
}
* {
    box-sizing: border-box;
}
.login-form2 form input[type="password"]:hover, .login-form2 form input[type="text"]:hover, .login-form2 form input[type="file"]:hover, .login-form2 form input[type="email"]:hover, .login-form2 form input[type="number"]:hover {
    border: 1px solid #EC6E3C;
}
.login-form2 form input[type="text"], .login-form2 form input[type="email"] {
    background: transparent url("../images/msg.png") no-repeat scroll 15px 13px;
    border: 1px solid #C2C2C2;
}
.login-form2 form input[type="text"], .login-form2 form input[type="password"], .login-form2 form input[type="file"], .login-form2 form input[type="email"], .login-form2 form input[type="number"] {
    margin: 0px 0px 30px;
    width: 100%;
    padding: 12px 15px 12px 50px;
    font-size: 1.2em;
    outline: medium none;
    color: #373737;
}
.login-form2 form input[type="password"] {
    background: transparent url("../images/key.png") no-repeat scroll 15px 14px;
    margin-bottom: 0px;
	
    border: 1px solid #C2C2C2;
}
.login-form2 h3 {
    margin: 0px 0px 30px;
    font-size: 1.6em;
    color: #373737;
    text-align: p;
}
h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
    font-family: inherit;
    font-weight: 500;
    line-height: 1.1;
    color: inherit;
}
.login-pad {
    padding: 3em 3em 30px;
}
#user{
	 background: transparent url("images/user.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/user.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 14px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
#userid{
	 background: transparent url("images/5.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/5.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 14px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
#nor{
	 background: transparent url("images/mobile.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/mobile.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 1px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
#email{
	 background: transparent url("images/email.jpg") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/email.jpg");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 10px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
#pw1,#pw2{
	 background: transparent url("images/m2.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/m2.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 10px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
#file{
	 background: transparent url("images/upload.png") no-repeat scroll 15px 14px;
        background-color: transparent;
        background-image: url("images/upload.png");
        background-repeat: no-repeat;
        background-attachment: scroll;
        background-position: 15px 10px;
        background-clip: border-box;
        background-origin: padding-box;
        background-size: auto auto;
    margin-bottom: 0px;
    border: 1px solid #C2C2C2;
}
</style>
<a href="#" id="refresh" style="display:none"></a>
<script>
						$(document).ready(function(){
							var url=$(location).attr('href');
							
								 $("#reg").click(function(){
									 $("#login").css({"display":"none"});
									$("#register").css({"display":"initial"});
								});
								$("#signin").click(function(){
									$("#register").css({"display":"none"});
									$("#login").css({"display":"initial"});
								});
								if(url.match(/register/i)!=null)
								{
									$("#login").css({"display":"none"});
									$("#register").css({"display":"initial"});
								}
								else
								if(url.match(/login/i)!=null)
								{
									$("#register").css({"display":"none"});
									$("#login").css({"display":"initial"});
									
								}
								else
								{
									$("#register").css({"display":"none"});
									$("#login").css({"display":"initial"});
									
								}

							
						});
</script>
	<!-- content -->
	<div class="content">
		<div class="container">
		<br /><br />
		<p>
		<!-------------------------------------------------------->
			<div id="login">
				<a style="display:none"  id="tologin"></a>
					<div class="col-md-4 login-form2" id='adm'>
						<div class="login-pad">
						<br /><br /><br /><br />
							<h3>MEMBER LOGIN</h3>
							<form  action="login.php" method="post">
								<input type="text" value="Username" name="user"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Username';}" required>
								<input type="password" value="Password" name="pwd" onfocus="this.select()" onblur="if (this.value == '') {this.value = 'Password';}" required>							
								<p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" value="loginkeeping" /> 
									<label for="loginkeeping">Keep me logged in</label>
								</p>
								<center><input style='font-family:guru' type="submit" value="SIGN IN"></center>
								<br />
							</form>
							<p>
								<b style='color:#337AB7'><a href="#toregister" id="reg">Register</a></b>
							</p>
							<br /><br /><br />
						</div>
					</div>
				</div>
	<!---------------------------------------------------------------->			
				
	
					<div id="register">
						<script>
								
								$(document).ready(function(){
									// [a-zA-Z0-9_-.+]+@[a-zA-Z0-9-]+.[a-zA-Z]+
									// /\S+@\S+\.\S+/
									validate();
									$("input").mouseout(function(){
										 validate();
										 });
									$("submit").mouseover(function(){
										 validate();
									});
									 $("form").mouseover(function(){
										 validate();
									});
										 function validate()
										 {
											 var note="<br />";
											 var error=0;
											 
											var user= $("#user").val();
											if((user.length<5) || user.match(/Full name/i)!=null){error++;note+="length of Full name is very small<br />";}
											
											var user= $("#userid").val();
											if((user.length<4) || user.match(/Username/i)!=null){error++;note+="length of user name is very small<br />";}
											 
											var eid= $("#email").val();
											var emailid=eid.match(/\S+@\S+\.\S+/ig);
											if(emailid==null){error++;note+="Not a valid Email Id<br />";}
											
											var n= $("#nor").val();
											var nor=n.match(/[0-9]{10}/);
											if(nor==null){error++;note+="Not a valid Mobile Number<br />";}
																						
											var pw1= $("#pw1").val();
											var p1=pw1.match(/[a-zA-Z0-9]{4,10}/);
											if(p1==null){error++;note+="First password is not valid<br />";}
											
											var pw2= $("#pw2").val();
											var p2=pw2.match(/[a-zA-Z0-9]{4,10}/);
											if(p2==null){error++;note+="Second password is not valid<br />";}
											
											if(pw1!=pw2){error++;note+="Password mismatch<br />";}
																						
											//alert(note);
											if(error>0)
											{
												$("#error").html(note);
												$("#error").css({"color":"red"});
												$('#test').bind('click', function(e) {
												e.preventDefault(); // prevents the form from being submitted
												});
											}
											else
											{ 
												$("#error").html("OK");
												$("#error").css({"color":"lime"});
												$("#test").click(function(){
													$('form').trigger("submit");
												});
											}
										 }
								});
						</script>
						<div class="col-md-4 login-form2" id='adm'>
						<a id="toregister" style="display:none"></a>
						<div class="login-pad">
						<br /><br /><br /><br />
							<h3>MEMBER REGISTRATION</h3>
                            <form  action="register.php" method="post" autocomplete="on"  enctype="multipart/form-data"> 
                                <input type="text" id="user" value="Full name" name="name" placeholder="Full name"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Full name';}" required='required'> 
                                <input type="text" value="Username" name="user" id="userid" placeholder="Username" maxlength="10"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Username';}" required='required'> 
								 <input type="text" id="email" value="Email Id" name="email" placeholder="Email Id" onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Email Id';}" required='required'> 
                                 <input type='text' id="nor" value="Mobile number" name="phno" placeholder="Mobile number" maxlength="10"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Mobile number';}" required='required'> <br /><br /><br />
                                <input type="password" value="" name="pwd1" id="pw1"  placeholder="Enter password" maxlength="10"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = '';}" required='required'>
                                <input type="password" value="" name="pwd2" id="pw2" maxlength="10"  placeholder="Confirm password"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = '';}" required='required'> <br /><br /><br />
                                <input type="file" id="file" value="" name="fileToUpload"  onfocus="this.value='';this.select()" onblur="if (this.value == '') {this.value = 'Username';}"> 
								<i style='color:red' id="error"></i>
								<input style='font-family:guru' type="submit" value="SIGN UP" id="test">
							</form>
							<p>
								<b style='color:#337AB7'><a href="#tologin" id="signin">LOGIN</a> </b>
							</p>
							<br /><br /><br />
						</div>
					</div>
                </div>
						
		<!---------------------------------------------------------------->		
		</p>
				<br /><br /><br /><br />
				<div class="clearfix"></div>
			</div>
	</div>

					
					
						
</body>
</html>