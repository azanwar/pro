<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sign Up</title>
		<script>
		function validate(){
		
			document.getElementById("unError").setAttribute("style","display:none;");
			document.getElementById("pwError").setAttribute("style","display:none;");
			var empty = false;
			var un = document.form.username.value.trim();
			if (!un||un===""){
				document.getElementById("unError").innerHTML = "Username cannot be empty";
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
				xhttp.open("GET","Signup?username="+un+"&password="+pw,false);
				xhttp.send();
				if (xhttp.responseText.trim().length > 0){
					document.getElementById("unError").innerHTML = xhttp.responseText;
					document.getElementById("unError").setAttribute("style","display:inline;color:red;");

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
		<h1>Sign Up</h1>
		<form name = "form" action="profile.jsp" method="POST" onsubmit="return validate();">
			Username: <input type="text" name="username" /><span id = "unError" style="display:none;color:red;"></span><br />
			<br />
			Email: <input type="text" name="email" /><span id = "emailError" style="display:none;color:red;"> Email cannot be empty!</span><br />
			<br />
			Password: &nbsp;<input type="password" name = "password" /><span id = "pwError" style="display:none;color:red;"> Password cannot be empty!</span><br />
			<br />
			<input type="submit" name="submit" value = "Submit" />
		</form>
	</body>
	</body>
</html>