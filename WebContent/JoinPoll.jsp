<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Join Poll</title>
		<script>
	  // Initialize Firebase
	  var socket;
		socket = new WebSocket("ws://localhost:8080/CSCI201_ProPoll_Project/ws");
	  	function sendMessage(){
	  		socket.send("update page");
	  		return true;
	  	}
	  function reader() {
			document.title = "<%=request.getAttribute("name")%>";
			document.getElementById("name").innerHTML = "<%=request.getAttribute("name")%>";
			<% for (int i=0; i<(Integer)session.getAttribute("numquestions"); i++) {%>
		    document.getElementById("q<%=i%>").innerHTML = "<%=request.getAttribute("question"+Integer.toString(i))%>";
		    <% for (int j=0; j<4; j++) { %>
		    	document.getElementById("r<%=(i*10)+j%>").innerHTML = "  "+"<%=request.getAttribute("response"+Integer.toString((i*10)+j))%>";
		    <% } }%>
	  }
	  </script>
	</head>
	<body>
		<script>
		<% if (request.getParameter("invalid")!=null&&request.getParameter("invalid").equals("true")) { %>
			document.getElementById("error").innerHTML = "Please choose a response for all questions!";
		<% }
		session.setAttribute("pollID", request.getParameter("pollID"));
		session.setAttribute("pollName", request.getParameter("pollName"));
		%>
		</script>
		<h1 id="name"></h1>
		<form method="GET" action="JoinPollResponsesServlet" onsubmit="return sendMessage();">
		<div id="error" style="color:red;"></div>
		<% for (int i=0; i<(Integer)session.getAttribute("numquestions"); i++) {%>
			<pre><span id="q<%=i%>"></span><br /></pre>
			<% for (int j=0; j<4; j++) {%>
				<pre>	<input name="question<%=i%>" type="radio" id="response<%=(i*10)+j%>" value="<%=j%>"><span id="r<%=(i*10)+j%>"></span><br /></pre>
			<% } %>
		<% } %>
		<input type="submit" id="submit">
		</form>
		<script>reader();</script>
	</body>
</html>