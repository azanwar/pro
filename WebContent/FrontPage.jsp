<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Pro Poll</title>
	</head>
	<body>
	<% if (request.getParameter("logout")==null) {
		session.setAttribute("loggedOn", null);
	}
	%>
	<script>
		function validate() {
			var req = "JoinPollValidationServlet?pollID="+document.getElementById("pollID").value;
	  
		  	var xhttp = new XMLHttpRequest();
			xhttp.open("POST", req , false);
			xhttp.send();
			
			if (xhttp.responseText.trim().length > 0){
				document.getElementById("error").innerHTML = xhttp.responseText;
				return false;
			}
			else {
				return true;
			}
		}
	</script>
	
		<!-- NAVBAR -->
		<nav class="blue">
	        <div class="nav-wrapper">
	          <a href="#" class="brand-logo white-text">ProPoll</a>   
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
	                <li><a href="login.jsp" class="white-text">Login</a></li>
	                <li><a href="signup.jsp" class="white-text">Sign Up</a></li>
	            </ul>
	        </div>
	      </nav> 
	      <!-- NAVBAR -->
      	<div class ="container" >
      		<div class ="row">
      			<div class="col s6 offset-s3 z-depth-2" id="panell">
					<form method="POST" action="JoinPollServlet" onsubmit="return validate();">
						<div id="error" style="color:red;"></div>
						Please enter poll ID<input type="text" id= "pollID" name="pollID">
						<button class="btn waves-effect waves-light" type="submit" name="submit" value ="Submit">Search
						   <i class="material-icons right"></i>
						</button>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>