<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Pro Poll</title>
	</head>
	<body>
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
	<a href="login.jsp"> Log in</a>
	<a href="signup.jsp" >Sign up</a>
		<form method="POST" action="JoinPollServlet" onsubmit="return validate();">
			<div id="error" style="color:red;"></div>
			Please enter poll ID<input type="text" id= "pollID" name="pollID">
			<input name="submit" type="submit">
		</form>
	</body>
</html>