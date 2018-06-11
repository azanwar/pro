<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>${pollData.getName()} Results</title>
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
    
    var socket;
	function connectToServer() {
		socket = new WebSocket("ws://localhost:8080/CSCI201_ProPoll_Project/ws");
		socket.onopen = function(event) {
			document.getElementById("mychat").innerHTML += "Connected!";
		}
		socket.onmessage = function(event) {
			
			document.getElementById("mychat").innerHTML += event.data + "<br />";
			/*
			 google.charts.load('current', {'packages':['corechart']});

		      // Set a callback to run when the Google Visualization API is loaded.
		      google.charts.setOnLoadCallback(drawChart);

			drawChart(); 
			*/
		  	 location.reload(true);
		}
		socket.onclose = function(event) {
			document.getElementById("mychat").innerHTML += "Disconnected!";
		}
	}

    
    
    var w;

    function startWorker() {
        if(typeof(Worker) !== "undefined") {
            if(typeof(w) == "undefined") {
                w = new Worker("demo_workers.js");
            }
            w.onmessage = function(event) {
                document.getElementById("result").innerHTML = event.data;
                
           
              
            };
        } else {
            document.getElementById("result").innerHTML = "Sorry! No Web Worker support.";
        }
    }

    function stopWorker() { 
        w.terminate();
        w = undefined;
       
       
    }
    
    startWorker();
  //  stopWorker();

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {
    	  resultArray = [];
    	  
    	  
    /*  for (int i = 0; i <= parseInt('${pollData.size()}'); i++){*/
    		  
    	resultArray = [];
    		  
    		  
    	  
    	var title =   '${pollData.getQuestion(0).toString() }';
    	console.log(title)
    	
    	var questionSize = parseInt('${pollData.getQuestion(i).size() }');
    
    	console.log(questionSize);
    	var j;
    	var count = 0;
    	
    	

    	var res1  = '${pollData.getQuestion(0).getResponseString(0)}';
    	var res1Num1 = parseInt('${pollData.getQuestion(0).getResponseCount(0)}');
    	resultArray.push([res1, res1Num1]);
    	var res2  = '${pollData.getQuestion(0).getResponseString(1)}';
    	var res1Num2 = parseInt('${pollData.getQuestion(0).getResponseCount(1)}');
    	resultArray.push([res2, res1Num2]);
    	
    	var res3  = '${pollData.getQuestion(0).getResponseString(2)}';
    	var res1Num3 = parseInt('${pollData.getQuestion(0).getResponseCount(2)}');
    	resultArray.push([res3, res1Num3]);
    	var res4  = '${pollData.getQuestion(0).getResponseString(3)}';
    	var res1Num4 = parseInt('${pollData.getQuestion(0).getResponseCount(3)}');
    	resultArray.push([res4, res1Num4]);
    	
    	
		var data = new google.visualization.DataTable();
        data.addColumn('string', 'ResponseName');
        data.addColumn('number', 'ResponseNumber');
       // final add 
        data.addRows(resultArray);
 
        // Set chart options
        var options = {'title':title,
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        console.log("drawwwwwwww");
    //}
      }
    </script>
	</head>
	<body onload="connectToServer()">
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
	                <li><a href="#" class="white-text">Log Out</a></li>
	            </ul>
	        </div>
	     </nav> 
		<!-- NAVBAR -->
		<div align = "center">
			<h1> ${pollData.getName() } Results</h1>	
		</div>

		<div class = "container" padding = "30px">
			<div class = "row">
				<div class="col s16 m4 l2">
					<div id="yourResponses" >
						<c:if test="${loggedOn!=null}" >
							<h5>Your responses</h5>
							<ol>
								<c:if test="${userResponse.size()>0}">
								<c:forEach var="i" begin="0" end="${userResponse.size()-1}">
								<li>
								${userResponse.get(i).toString()} : ${userResponse.get(i).getResponseString(0)}
								</li>
								</c:forEach>
								</c:if>
							</ol>
						</c:if>
					</div>
		
					<c:if test="${loggedOn.equals(pollData.getCreator())}" >
					<h1> User Responses</h1>
					<c:if test="${pollResponses.size()>0 }">
						<c:forEach var="i" begin ="0" end="${pollResponses.size() -1}">
							${pollResponses.get(i) } <br />
						</c:forEach>
					</c:if>
					</c:if>
				</div>
			<div class="col s16 m4 l2">
				<h5> Total Responses</h5>
				<ol>	
					<c:if test="${pollData.size()>0}">
					<c:forEach var="i" begin="0" end = "${pollData.size()-1}">
						<li> ${pollData.getQuestion(i).toString() } </li>
						<ul>
						<c:forEach var="j" begin="0" end="${pollData.getQuestion(i).size()-1 }">
							<li> 
							${pollData.getQuestion(i).getResponseString(j)} : ${pollData.getQuestion(i).getResponseCount(j)}
							</li>
						</c:forEach>
						</ul>
					</c:forEach>
					</c:if>
				</ol>
			</div>
			<div class="col s16 m4 l2">
	    		<div id="chart_div"></div>
	    	</div>
	  	</div>
	    <p>It's been: <output id="result"></output> seconds since the last update</p>




	<div id="mychat"></div>


	<div id="disqus_thread"></div>
	<script>
		/**
		 *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
		 *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables*/

		var disqus_config = function() {
 			this.page.url = 'http://example.com'; // Replace PAGE_URL with your page's canonical URL variable
			//this.page.url = "{{site.url}}{{page.url}}"; // Replace PAGE_URL with your page's canonical URL variable
			this.page.identifier = "{{page.url}}"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
		};

		(function() { // DON'T EDIT BELOW THIS LINE
			var d = document, s = d.createElement('script');
			s.src = 'https://pollpro.disqus.com/embed.js';
			s.setAttribute('data-timestamp', +new Date());
			(d.head || d.body).appendChild(s);
		})();
	</script>
	<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
	</body>
</html>