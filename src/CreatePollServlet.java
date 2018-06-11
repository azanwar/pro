

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
		
		//permitted users
		HashSet<String> permitted = new HashSet<String>();
		for (int i=0; i<Integer.parseInt((String)request.getParameter("numPermitted")); i++) {
			permitted.add(request.getParameter("permitteduser"+Integer.toString(i)));
		}
		
		Poll poll = new Poll(name, -20, (String)request.getSession().getAttribute("loggedOn"), permitted); 
		
		//questions and responses
		for (int i=0; i<Integer.parseInt((String)request.getSession().getAttribute("numquestions"))-1; i++) {
			Question q = new Question(request.getParameter("question"+Integer.toString(i)));
			for (int j=0; j<4; j++) {
				q.addResponse(request.getParameter("response"+Integer.toString((i*10)+j)), 0);
			}
			poll.addQuestion(q);
		}
	
		//write to database
		ProPollServer.addPoll(poll, (String)request.getSession().getAttribute("loggedOn"));
		request.setAttribute("pollName", poll.getName());
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/PollResults");
		dispatch.forward(request, response);
	}

}
