<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
		<link rel="stylesheet" href="profile.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>${loggedOn}'s profile</title>
		<script>
			function redirect() {
				document.location.href = "CreatePoll.jsp?new=true";
			}
			
			function redirect2() {
				document.location.href = "FrontPage.jsp?logout=false";
			}
			
			function logout() {
				document.location.href = "FrontPage.jsp";
			}
			
		</script>
	</head>
	<body>
		<!-- NAVBAR -->
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
	                <li>
	                			<button class="waves-effect waves-light btn" type="button" onclick="logout()" name="logout" id="logout">Logout</button>
	                </li>
	            </ul>
	        </div>
	     </nav> 
		<!-- NAVBAR -->
		<!-- HEADER -->
		<header class="header indigo z-depth-1">
			<div class = "profile" align = "center">
		        <img align = "center" height = "200px" width = "200px" src="https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png" alt="" class="circle responsive-img">
		        <h3>${loggedOn}'s Polls</h3>
	        </div>
	    </header>
	    <!-- HEADER -->

		<div class = "container" padding = "30px">
			<div class = "row">
		      	<div class="col s12 m4 l2">
					<h2>Polls Joined</h2>
					<button class="waves-effect waves-light btn" type="button" name="participate" onclick="redirect2()">Join Poll</button>
					<c:choose>
					<c:when test="${joinedPolls.isEmpty()}">
						<h2> No polls</h2>
					</c:when>
					<c:otherwise>
					<form action="PollResults" method="GET">
						<ol>
							<c:if test="${createPolls.size()>0}">
							<c:forEach var="i" begin = "0" end = "${joinedPolls.size()-1 }">
								<li>
									<button type="submit" name="pollName" value="${joinedPolls.get(i)}">${joinedPolls.get(i)} </button>
								</li>
								<div class="pollDropDown" >
									
								
								</div>
							</c:forEach>
						</c:if>
						</ol>
					</form>
					</c:otherwise>
					</c:choose>
				</div>
		
		      	<div class="col s12 m4 l2">
					<h2> Polls Created</h2>
					<button class="waves-effect waves-light btn" type="button" name="create" onclick="redirect()">Create New Poll</button>
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
				</div>

		      	<div class="col s12 m4 l2">
					<h2>Invited Polls</h2>
					<c:choose>
					<c:when test="${invPolls.isEmpty()}">
						<h5> No polls</h5>
					</c:when>
					<c:otherwise>
					<form action="PollResults" method="GET">
						<ol>
							<c:if test="${invPolls.size()>0}">
							<c:forEach var="i" begin = "0" end = "${invPolls.size()-1 }">
								<li>
									<button type="submit" name="pollName" value="${invPolls.get(i)}">${invPolls.get(i)} </button>
								</li>
								<div class="pollDropDown" >
									
								
								</div>
							</c:forEach>
							</c:if>
						</ol>
					</form>
					</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>

	</body>
</html>