<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Create Poll</title>
	</head>
	<body>
		<% if (request.getParameter("new")!=null&&request.getParameter("new").equals("true")){ //have some kind of request parameter which specifies whether or not the poll is new instead of checking for null
			session.setAttribute("numquestions", "1");
		} %>
		<script>
		  function check() {
		      if (document.getElementById("public").checked) {
		    	  document.getElementById("allowedlabel").style.display = 'none';
		          document.getElementById("allowedusers").style.display = 'none';
		      } else {
		    	  document.getElementById("allowedlabel").style.display = 'block';
		          document.getElementById("allowedusers").style.display = 'block';
		      }
		  }
		  
		  function writer() {
			  
			  	//send AJAX request to servlet 
			  	var req = "CreatePollServlet?name="+document.getElementById("name").value;
			  	
			  	var permitted = document.getElementById("allowedusers").value.split(", ");
			  	req += "&numPermitted="+permitted.length;
			  	for (var i=0; i<permitted.length; i++) {
			  		req+="&permitteduser"+i+"="+permitted[i];
			  	}
			  	
			  	var num = <%=Integer.parseInt((String)session.getAttribute("numquestions"))%>;
	  			for (var k=0; k<num; k++) {
	  				req+="&question"+k+"="+document.getElementById("question"+k).value;
	  				  
	  			for (var j=0; j<4; j++) {
	  				req+="&response"+((k*10)+j)+"="+document.getElementById("response"+((k*10)+j)).value;
	  			} }
		  
			  	var xhttp = new XMLHttpRequest();
				xhttp.open("POST", req , true);
				xhttp.send();
				
				//add error checking?
				
	  		}
		  
		  </script>
		<form action="CreatePoll.jsp" method="POST">
		<% if (request.getParameter("name")!=null) {%>
			Name: <input id="name" name="name" type="text" value="<%=request.getParameter("name")%>">
		<% } else { %>
			Name: <input id="name" name="name" type="text">
		<% } %>
			<br />
			Poll type:
			<% if (request.getParameter("polltype")!=null&&request.getParameter("polltype").equals("private")) {%>
			<input type="radio" onclick="check()" name="polltype" id="public" value="public">Public
			<input type="radio" onclick="check()" name="polltype" id="private" value="private" checked>Private
			<% } else { %>
			<input type="radio" onclick="check()" name="polltype" id="public" value="public" checked>Public
			<input type="radio" onclick="check()" name="polltype" id="private" value="private">Private
			<% } %>
			<br />
			<div id="allowedlabel"><br />Allowed users:<br /></div>
			<% if (request.getParameter("allowedusers")!=null) { %>
			<textarea id="allowedusers" name="allowedusers" rows="10" cols="80"><%=request.getParameter("allowedusers")%></textarea>
			<% } else { %>
			<textarea id="allowedusers" name="allowedusers" rows="10" cols="80"></textarea>
			<% } %>
			<br />
			<% for (int i=0; i<Integer.parseInt((String)session.getAttribute("numquestions")); i++) {
				if (request.getParameter("question"+i)==null){%>
				<pre>Question <%=i+1%>: <input name="question<%=i%>" id="question<%=i%>" class="question" type="text"><br /></pre>
				<% } else { %>
				<pre>Question <%=i+1%>: <input name="question<%=i%>" id="question<%=i%>" class="question" type="text" value="<%=request.getParameter("question"+Integer.toString(i))%>"><br /></pre>
				<% }
				for (int j=0; j<4; j++) {
				 if (request.getParameter("response"+Integer.toString((i*10)+j))==null){ %>
				 <pre>	Option <%=j+1%>: <input name="response<%=(i*10)+j%>" id="response<%=(i*10)+j%>" class="answer" type="text"><br /></pre>
				 <% } else {%>
				 <pre>	Option <%=j+1%>: <input name="response<%=(i*10)+j%>" id="response<%=(i*10)+j%>" class="answer" type="text" value="<%=request.getParameter("response"+Integer.toString((i*10)+j))%>"><br /></pre>
			<% } } }%>
			<%
				session.setAttribute("numquestions", Integer.toString(Integer.parseInt((String)session.getAttribute("numquestions"))+1));
			%>
			<input type="submit" id="addquestion" value="Add Question">
		</form>
			<button id="submit" onclick="writer()">Submit</button>
			<script>check();</script>
	</body>
</html>