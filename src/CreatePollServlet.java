

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.util.Pair;

/**
 * Servlet implementation class CreatePollServlet
 */
@WebServlet("/CreatePollServlet")
public class CreatePollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//name
		String name = request.getParameter("name");
		if (name==null||name.equals("")) {
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/CreatePoll.jsp?new=true&invalid=true");
			dispatch.forward(request, response);
			return;
		}
		
		//permitted users
		HashSet<String> permitted = new HashSet<String>();
		String allowed = request.getParameter("allowedusers");
		String[] allowedUsers = allowed.split(",");
		for (int i=0; i<allowedUsers.length; i++) {
			System.out.println(allowedUsers[i]);
			permitted.add(allowedUsers[i].trim());
		}
		
		Poll poll = new Poll(name, -20, (String)request.getSession().getAttribute("loggedOn"), permitted);
		poll.setPrivPoll(request.getParameter("polltype").equals("private"));
		
		//questions and responses
		if (request.getParameter("question0").equals("")) {
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/CreatePoll.jsp?new=true&invalid=true");
			dispatch.forward(request, response);
			return;
		}
		for (int i=0; i<Integer.parseInt((String)request.getSession().getAttribute("numquestions"))-1; i++) {
			Question q = new Question(request.getParameter("question"+Integer.toString(i)));
			for (int j=0; j<4; j++) {
				q.addResponse(request.getParameter("response"+Integer.toString((i*10)+j)), 0);
			}
			poll.addQuestion(q);
		}
	
		//write to database
		ProPollServer.addPoll(poll, (String)request.getSession().getAttribute("loggedOn"));
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/PollResults?pollName="+poll.getName());
		dispatch.forward(request, response);
	}

}
