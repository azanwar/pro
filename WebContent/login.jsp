<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
	<link rel="stylesheet" href="login.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
	<script>
		function validate(){
			document.getElementById("unError").setAttribute("style","display:none;");
			document.getElementById("pwError").setAttribute("style","display:none;");
			var empty = false;
			var un = document.form.username.value.trim();
			if (!un||un===""){
				document.getElementById("unError").setAttribute("style","display:inline;color:red;");
				empty=true;	
			}
			var pw = document.form.password.value.trim();
			if (!pw||pw===""){
				document.getElementById("pwError").setAttribute("style","display:inline;color:red;");
				empty=true;
			}
			if (!empty){
				var xhttp = new XMLHttpRequest();
				xhttp.open("GET","Login?username="+un+"&password="+pw,false);
				xhttp.send();
				if (xhttp.responseText.trim().length > 0){
					document.getElementById("loginError").innerHTML = xhttp.responseText;
					return false;
				}
				else{
					return true;
				}
			}
			return false;
		}
	</script>
</head>
	<body>
		<!--NAVBAR-->
		<nav class="blue">
	        <div class="nav-wrapper">
	          <a href="FrontPage.jsp" class="brand-logo white-text">ProPoll</a>   
	            <ul class="hide-on-med-and-down right">               
	                <li>    
	                   <div class="center row">
	                      <div class="col s12 " >
	                        <div class="row" id="topbarsearch">
	                          <div class="input-field col s6 s12 blue-text" text-align = "center">
	                            <i class="red-text material-icons prefix"></i>
	                            	<input type="text" placeholder="Enter Poll ID" id="autocomplete-input" class="autocomplete red-text" 
	                            		name = "pollID" method = "GET" action = "JoinPollServlet">
	                            </div>
	                          </div>
	                        </div>
	                      </div>          
	                  </li>                     
	                <li><a href="signup.jsp" class="white-text">Sign Up</a></li>
	            </ul>
	        </div>
	     </nav> 
	     <!--NAVBAR-->
		<div class = "container" vertical-align = "center">
			<div class = "row">
				<div class="col s6 offset-s3 z-depth-2" id="panell">
					<h5 id="title">Login</h5>
					<form name = "form" action="Profile" method="POST" onsubmit="return validate();">
						<p id="loginError" style="color:red;"></p>
						<input placeholder = "Username" type="text" name="username" /><span id = "unError" style="display:none;color:red;"> Username cannot be empty!</span><br />
						<br />
						&nbsp;<input placeholder = "Password" type="password" name = "password" /><span id = "pwError" style="display:none;color:red;"> Password cannot be empty!</span><br />
						<br />
						  <button class="btn waves-effect waves-light" type="submit" name="submit" value ="Submit">Login
						    <i class="material-icons right"></i>
						  </button>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>