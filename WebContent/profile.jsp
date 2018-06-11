<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>${loggedOn}'s profile</title>
		<script>
			function redirect() {
				document.location.href = "CreatePoll.jsp?new=true";
			}
			
			function redirect2() {
				document.location.href = "FrontPage.jsp";
			}
		</script>
	</head>
	<body>
		<h1> ${loggedOn}'s profile</h1>
		<button type="button" name="create" onclick="redirect()">Create New Poll</button>
		<button type="button" name="participate" onclick="redirect2()">Participate in Another Poll</button>
		<h2>Polls Joined</h2>
		<c:choose>
		<c:when test="${joinedPolls.isEmpty()}">
			<h2> No polls</h2>
		</c:when>
		<c:otherwise>
		<form action="PollResults" method="GET">
			<ol>
				<c:forEach var="i" begin = "0" end = "${joinedPolls.size()-1 }">
					<li>
						<button type="submit" name="pollName" value="${joinedPolls.get(i)}">${joinedPolls.get(i)} </button>
					</li>
					<div class="pollDropDown" >
						
					
					</div>
				</c:forEach>
			</ol>
		</form>
		</c:otherwise>
		</c:choose>
		
		<h2> Polls Created</h2>
			<c:choose>
		<c:when test="${createdPolls.isEmpty()}">
			<h2> No polls</h2>
		</c:when>
		<c:otherwise>
		<form action="PollResults" method="GET">
			<ol>
				<c:forEach var="i" begin = "0" end = "${createdPolls.size()-1 }">
					<li>
						<button type="submit" name="pollName" value="${createdPolls.get(i)}">${createdPolls.get(i)} </button>
					</li>
					<div class="pollDropDown" >
						
					
					</div>
				</c:forEach>
			</ol>
		</form>
		</c:otherwise>
		</c:choose>
	</body>
</html>